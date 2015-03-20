package cn.com.cis.persistence;

import cn.com.cis.domain.JobConfig;

import java.util.List;

public interface JobConfigMapper {

    void insertJobConfig(JobConfig jobConfig);

    void updateJobConfig(JobConfig jobConfig);

    void delJobConfig(int id);

    JobConfig queryJobConfigById(int id);

    List<JobConfig> queryAllJobs();
}
