package cn.com.cis.job;

import cn.com.cis.common.ILifeCycle;
import cn.com.cis.domain.JobInfo;
import cn.com.cis.job.split.SplitFactory;
import cn.com.cis.job.split.Splittable;
import cn.com.cis.task.Task;
import cn.com.cis.task.TaskFactory;
import cn.com.cis.task.entity.TaskInfo;
import cn.com.cis.utils.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 作业执行者
 */
public class SimpleJobExecutor implements JobExecutor, ILifeCycle {

    private Logger logger = LoggerFactory.getLogger(SimpleJobExecutor.class);

    private ExecutorService executorService;
    private CompletionService<Long> completionService;

    private static final int MAX_INIT_NUM = 3;

    /**
     * 统计作业
     */
    private TaskStateJob taskStateJob;

    private JobInfo jobInfo;

    private volatile AtomicBoolean isError;
    private volatile AtomicInteger submitSize;
    private volatile AtomicInteger completSize;
    private volatile AtomicLong updatedNumber;
    private String errorMessage = "";
    private volatile AtomicLong beginTime;
    private Throwable error;

    public SimpleJobExecutor(JobInfo jobInfo) {
        this(Runtime.getRuntime().availableProcessors(), jobInfo);
    }

    public SimpleJobExecutor(int nThreads, JobInfo jobInfo) {
        executorService = Executors.newFixedThreadPool(nThreads);
        completionService = new ExecutorCompletionService<Long>(executorService);
        taskStateJob = new TaskStateJob(completionService);
        this.jobInfo = jobInfo;
    }

    @Override
    public void execute() throws Exception {

        if (jobInfo.isTruncateFlag()) {
            truncateTable();
        }
        // 1.分割作业
        Splittable jobSplit = SplitFactory.getSplittableInstance(jobInfo);
        // 2.将作业提交至completionService执行
        List<TaskInfo> taskInfos = jobSplit.split();
        for (TaskInfo taskEntity : taskInfos) {
            Task<Long> task = TaskFactory.getTaskInstance(taskEntity);
            completionService.submit(task);
            addSubmitTask();
        }
    }

    @Override
    public long getUpdateCount() {
        return updatedNumber.get();
    }

    @Override
    public boolean isDone() {
        return submitSize.get() == completSize.get();
    }

    @Override
    public boolean isError() {
        return isError.get();
    }

    private void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    private void setErrorThrowable(Throwable t) {
        this.error = t;
    }

    public Throwable getErrorThrowable() {
        return error;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean shutdown() throws InterruptedException {
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        while (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
            logger.info("正在关闭线程池......");
        }
        logger.info("线程池已关闭.");
        taskStateJob.destroy();
        setErrorMessage("作业被用户终止执行");
        setError();
        return true;
    }

    @Override
    public String getProgress() {
        return completSize.get() + "/" + submitSize.get();
    }

    @Override
    public long getPower() {
        long currentTime = System.currentTimeMillis();
        int d = (int) ((currentTime - beginTime.get()) * 1000);
        if (d == 0) {
            return 0;
        }
        return updatedNumber.get() / d;
    }

    @Override
    public void init() {
        startTaskStateJob();
        isError = new AtomicBoolean(false);
        submitSize = new AtomicInteger(0);
        completSize = new AtomicInteger(0);
        updatedNumber = new AtomicLong(0l);
        beginTime = new AtomicLong(System.currentTimeMillis());
        logger.debug("初始化作业执行器.");
    }

    @Override
    public void destroy() {
        executorService.shutdownNow();
        taskStateJob.destroy();
        this.updatedNumber.set(0);
        logger.debug("作业执行器销毁.");
    }

    private void setError() {
        isError.set(true);
    }

    private synchronized void addAffectedNumber(Long num) {
        updatedNumber.set(this.updatedNumber.get() + num);
    }

    private void addCompletTask() {
        if (completSize.get() < 0) {
            completSize.set(completSize.get() + 2);
        } else {
            completSize.set(completSize.get() + 1);
        }
    }

    private void truncateTable() throws JobException {
        Connection connection = null;
        String sql = "";

        int ic = 0;
        while (ic < MAX_INIT_NUM) {
            try {
                connection = DataSourceFactory.getDataSource(jobInfo.getTargetDatabase()).getConnection();
                if (connection != null) {
                    ic = 0;
                    break;
                }
            } catch (SQLException e) {
                ic++;
                logger.warn("truncate table 异常，正在第{}重试链接", e.getMessage(), ic);
            }
        }
        sql = "TRUNCATE TABLE " + jobInfo.getTargetTable();
        if(connection != null) {
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.execute();
                connection.commit();
            } catch (Exception e) {
                logger.error("截断表异常:{}", sql, e);
                throw new JobException(e);
            } finally {
                try {
                    if (connection != null && !connection.isClosed())
                        connection.close();
                } catch (SQLException e) {

                }
            }
        }else {
            throw new JobException("截断表异常,数据库连接未成功初始化!");
        }
    }

    private void addSubmitTask() {
        submitSize.set(submitSize.get() + 1);
    }

    private void startTaskStateJob() {
        taskStateJob.init();
        Thread thread = new Thread(taskStateJob);
        thread.setName("TaskStateJob");
        thread.start();
    }


    private class TaskStateJob implements Runnable, ILifeCycle {

        private Logger logger = LoggerFactory.getLogger(TaskStateJob.class);

        private volatile AtomicBoolean isRun = new AtomicBoolean(true);

        private CompletionService<Long> completionService;


        public TaskStateJob(CompletionService<Long> service) {
            this.completionService = service;
        }

        @Override
        public void init() {
            logger.debug("初始化task统计作业。");
            isRun.set(true);
        }

        @Override
        public void destroy() {
            logger.debug("销毁task统计作业。");
            isRun.set(false);
        }

        @Override
        public void run() {
            while (isRun.get()) {
                try {
                    Future<Long> future = completionService.poll(2, TimeUnit.SECONDS);
                    if (future != null) {
                        logger.debug(Thread.currentThread().getName() + " ==> 获取到一个完成的Task,返回值为:{}", future.get());
                        Long result = future.get();
                        if (result != null) {
                            addAffectedNumber(result);
                            addCompletTask();
                        }
                    }
                } catch (InterruptedException e) {
                    logger.error("线程中断错误:", e);
                    setErrorMessage(e.getMessage());
                    Thread.currentThread().interrupt();
                    setError();
                    setErrorThrowable(e);
                } catch (ExecutionException e) {
                    logger.error("Task执行出现异常:", e);
                    String message = "获取Task执行结果出错，错误原因可能为：" + e.getMessage();
                    StackTraceElement[] messages = e.getStackTrace();
                    int length = messages.length;
                    for (int i = 0; i < length; i++) {
                        message += "ClassName:" + messages[i].getClassName() + "\n";
                        message += "getFileName:" + messages[i].getFileName() + "\n";
                        message += "getLineNumber:" + messages[i].getLineNumber() + "\n";
                        message += "getMethodName:" + messages[i].getMethodName() + "\n";
                        message += "toString:" + messages[i].toString() + "\n";
                    }
                    setErrorMessage(message);
                    setError();
                    setErrorThrowable(e);
                }
            }
        }
    }

}
