package cn.com.cis.domain;

import java.io.Serializable;

public class JobConfig implements Serializable, Cloneable {
    private static final long serialVersionUID = -3883809821752727648L;
    public static final String SCHEDULE_JOB_NAME_KEY = "SCHEDULE_JOB_NAME_KEY";
    /**
     * 任务id
     */
    private int jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务分组
     */
    private String jobGroup;
    /**
     * 任务状态 0禁用 1启用 2删除
     */
    private int jobStatus;
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;
    /**
     * etl文件
     */
    private String etlFileUrl;
    /**
     * 立即运行
     */
    private boolean autostart = false;
    /**
     * 任务描述
     */
    private String remark;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEtlFileUrl() {
        return etlFileUrl;
    }

    public void setEtlFileUrl(String etlFileUrl) {
        this.etlFileUrl = etlFileUrl;
    }


    public boolean isAutostart() {
        return autostart;
    }

    public void setAutostart(boolean autostart) {
        this.autostart = autostart;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", etlFileUrl='" + etlFileUrl + '\'' +
                ", autostart=" + autostart +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public JobConfig clone() throws CloneNotSupportedException {
        JobConfig jobConfig = new JobConfig();
        jobConfig.jobId = this.jobId;
        jobConfig.jobName = this.jobName;
        jobConfig.jobGroup = this.jobGroup;
        jobConfig.jobStatus = this.jobStatus;
        jobConfig.cronExpression = this.cronExpression;
        jobConfig.etlFileUrl = this.etlFileUrl;
        jobConfig.autostart = this.autostart;
        return jobConfig;
    }
}
