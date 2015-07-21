package cn.com.cis.job;

import cn.com.cis.common.ILifeCycle;
import cn.com.cis.domain.JobInfo;
import cn.com.cis.domain.JobLog;
import cn.com.cis.enums.Status;
import cn.com.cis.service.JobLogService;
import cn.com.cis.utils.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ETLServer implements Runnable {

    private org.slf4j.Logger log = LoggerFactory.getLogger(ETLServer.class);
    @Autowired
    private JobLogService logService;

    private volatile AtomicBoolean isRunning = new AtomicBoolean(true);

    private JobExecutor jobExecutor;

    private BlockingDeque<JobInfo> blockingDeque;

    private int nThreads;

    @Override
    public void run() {
        JobLog jobLog = null;
        while (isRunning.get()) {
            try {
                // 获取队列中提交的作业
                JobInfo jobEntity = blockingDeque.poll(2, TimeUnit.SECONDS);
                // 获取到作业
                if (jobEntity != null) {
                    //  插入日志 begin
                    jobLog = new JobLog();
                    jobLog.setJobName(jobEntity.getJobName());
                    jobLog.setBeginTime(new Date());
                    jobLog.setLogDate(new Date());
                    jobLog.setStatus(Status.RUNNING.toString());
                    jobLog.setUsername(jobEntity.getSubmitUser());
                    jobLog.setMessage(jobEntity.getJobName() + " 开始执行。");
                    logService.insertJobEntityLog(jobLog);
                    jobExecutor = new SimpleJobExecutor(nThreads, jobEntity);
                    ILifeCycle iLifeCycle = (ILifeCycle) jobExecutor;
                    iLifeCycle.init();
                    jobExecutor.execute();
                    while (!jobExecutor.isDone() && !jobExecutor.isError()) {
                        TimeUnit.SECONDS.sleep(10);
                        jobLog.setNbLine(jobExecutor.getUpdateCount());
                        jobLog.appendMessage("当前速度: " + jobExecutor.getPower());
                        jobLog.appendMessage("影响行数: " + jobExecutor.getUpdateCount());
                        jobLog.appendMessage("进度: " + jobExecutor.getProgress());
                        logService.updateJobEntityLog(jobLog);
                    }
                    Logger.info("正在执行作业: {}", jobEntity.getJobName());
                    Logger.info("{} 影响总行数: {}", jobEntity.getJobName(), jobExecutor.getUpdateCount());
                    Logger.info("当前抽取速度: {} n/s", jobExecutor.getPower());
                    Logger.info("抽取进度: {}", jobExecutor.getProgress());
                    jobLog.setNbLine(jobExecutor.getUpdateCount());
                    jobLog.appendMessage("当前速度: " + jobExecutor.getPower());
                    jobLog.appendMessage("影响行数: " + jobExecutor.getUpdateCount());
                    jobLog.appendMessage("进度: " + jobExecutor.getProgress());
                    jobLog.setEndTime(new Date());
                    jobLog.setStatus(Status.SUCCESS.toString());
                    logService.updateJobEntityLog(jobLog);
                    iLifeCycle.destroy();
                    if (jobExecutor.isError()) {
                        jobLog.appendMessage("执行作业出错: " + jobExecutor.getErrorMessage());
                        jobLog.setStatus(Status.ERROR.toString());
                        jobLog.setEndTime(new Date());
                        Logger.error("执行作业出错: {}", jobExecutor.getErrorMessage());
                        Logger.error("执行出错:{}", jobExecutor.getErrorThrowable());
                        // 停止调度服务
                        shutdownNow();
                        Logger.info("执行出错，服务器自动停止，如继续执行后续作业请再次启动调度服务器。");
                        jobLog.appendMessage(jobEntity.getJobName() + "执行出错，服务器自动停止，如继续执行后续作业请再次启动调度服务器。");
                        logService.updateJobEntityLog(jobLog);
                        //lockService.deleteJobEntityLockByJobId(jobEntity.getJobId());
                    }
                    //lockService.deleteJobEntityLockByJobId(jobEntity.getJobId());
                    // 未获取到作业，休眠30秒
                } else {
                    Logger.info("等待用户提交作业.");
                    TimeUnit.SECONDS.sleep(30);
                }

            } catch (Exception e) {
                if (jobExecutor != null) {
                    ILifeCycle i = (ILifeCycle) jobExecutor;
                    try {
                        i.destroy();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if(jobLog != null && jobLog.getId() > 0){
                    jobLog.appendMessage(e.getMessage());
                    jobLog.setStatus(Status.ERROR.toString());
                    jobLog.setEndTime(new Date());
                    logService.updateJobEntityLog(jobLog);
                }
                Logger.error("错误: ", e);
                try {
                    shutdownNow();
                } catch (InterruptedException e1) {
                   
                }
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean shutdownNow() throws InterruptedException {
        if (isRunning.get()) {
            isRunning.set(false);
            Logger.info("停止调度服务器......");
            return jobExecutor == null || jobExecutor.shutdown();
        } else {
            Logger.info("调度服务器已经停止......");
            return true;
        }
    }


    public void start() {

        if (!isRunning.get()) {
            isRunning.set(true);
            Thread thisThread = new Thread(this);
            thisThread.start();
            Logger.info("启动调度服务器......");
        } else {
            Logger.info("调度服务器已经启动......");
        }

    }

    public boolean getEtlServerStatus() {
        return isRunning.get();
    }

    public long getPower() {
        if (jobExecutor != null) {
            return jobExecutor.getPower();
        }
        return 0;
    }

    public void setBlockingDeque(BlockingDeque<JobInfo> blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    public BlockingDeque<JobInfo> getBlockingDeque() {
        return blockingDeque;
    }

    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

}
