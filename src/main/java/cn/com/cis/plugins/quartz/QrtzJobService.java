package cn.com.cis.plugins.quartz;

import cn.com.cis.domain.JobConfig;
import cn.com.cis.service.JobConfigService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Component
public class QrtzJobService {

    @Autowired
    private SchedulerFactoryBean cronScheduler;
    @Autowired
    private JobConfigService service;

    /**
     * 加载定时作业
     * @throws SchedulerException
     */
    public void loadTask() throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        //获取任务信息数据
        List<JobConfig> jobList = service.queryAllJobs();
        for (JobConfig job : jobList) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(QrtzJobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put(JobConfig.SCHEDULE_JOB_NAME_KEY, job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }
    }

    /**
     * 获取计划中的任务列表
     * @return 返回计划中的任务列表
     * @throws SchedulerException
     */
    public List<JobConfig> getScheduleJobs() throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<JobConfig> jobList = new ArrayList<JobConfig>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                JobConfig job = new JobConfig();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setRemark("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                //job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 获取执行中的任务列表
     * @return 返回执行中的任务列表
     * @throws SchedulerException
     */
    public List<JobConfig> getExecutingJobs() throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<JobConfig> jobList = new ArrayList<JobConfig>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            JobConfig job = new JobConfig();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setRemark("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

            //job.setJobStatus(triggerState.name());
            job.setJobStatus(triggerState.ordinal());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停任务
     * @param jobConfig 定时任务描述信息
     * @throws SchedulerException
     */
    public void pauseJob(JobConfig jobConfig) throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobConfig.getJobName(), jobConfig.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     * @param jobConfig 定时任务描述信息
     * @throws SchedulerException
     */
    public void resumeJob(JobConfig jobConfig) throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobConfig.getJobName(), jobConfig.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     * @param jobConfig 定时任务描述信息
     * @throws SchedulerException
     */
    public void deleteJob(JobConfig jobConfig) throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobConfig.getJobName(), jobConfig.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即运行一次
     * @param jobConfig 定时任务描述信息
     * @throws SchedulerException
     */
    public void immediateExecutorJob(JobConfig jobConfig) throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobConfig.getJobName(), jobConfig.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 刷新任务的时间表达式
     * @param jobConfig 定时任务描述信息
     * @throws SchedulerException
     */
    public void refreshJob(JobConfig jobConfig) throws SchedulerException {
        Scheduler scheduler = cronScheduler.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(jobConfig.getJobName(),
                jobConfig.getJobGroup());

        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig
                .getCronExpression());

        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();

        //按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

}
