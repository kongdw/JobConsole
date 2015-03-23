package cn.com.cis.persistence;


import cn.com.cis.domain.JobLog;

import java.util.List;

public interface JobLogMapper {

    void insertJobLog(JobLog jobExecuteLog);

    void updateJobLog(JobLog jobExecuteLog);

    List<JobLog> selectJobLogByJobName(String jobName);

    JobLog selectJobLogById(int id);

    List<JobLog> selectAllJobLog();
}
