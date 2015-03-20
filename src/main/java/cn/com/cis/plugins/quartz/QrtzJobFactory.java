package cn.com.cis.plugins.quartz;

import cn.com.cis.domain.JobConfig;
import cn.com.cis.plugins.scriptella.EtlExecutorBean;
import cn.com.cis.plugins.threadpool4j.ThreadPool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import scriptella.execution.EtlExecutorException;
import scriptella.execution.ExecutionStatistics;
import scriptella.interactive.ConsoleProgressIndicator;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class QrtzJobFactory implements Job {

    private static final Logger logger = LoggerFactory.getLogger(QrtzJobFactory.class);

    @Autowired(required = true)
    private ThreadPool threadPool;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobConfig jobConfig = (JobConfig) context.getMergedJobDataMap().get(JobConfig.SCHEDULE_JOB_NAME_KEY);
        logger.debug("执行定时任务: {}", jobConfig);
        EtlExecutorBean bean = new EtlExecutorBean();
        try {
            bean.setAutostart(jobConfig.isAutostart());
            bean.setConfigLocation(new UrlResource(new URL(jobConfig.getEtlFileUrl())));
            bean.setProgressIndicator(new ConsoleProgressIndicator(jobConfig.getJobName()));
            if (!jobConfig.isAutostart()) {
                Future<ExecutionStatistics> future =  threadPool.submit((Callable<ExecutionStatistics>) bean);
            } else {
                bean.execute();
            }
        } catch (IOException e) {
            throw new JobExecutionException(e);
        } catch (EtlExecutorException e) {
            throw new JobExecutionException(e);
        }
        logger.debug("定时任务执行结束: {}", jobConfig);
    }

}
