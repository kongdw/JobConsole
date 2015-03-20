package cn.com.cis.service;

import cn.com.cis.domain.JobConfig;
import cn.com.cis.persistence.JobConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobConfigService {

    @Autowired
    private JobConfigMapper mapper;

    public void insertJobConfig(JobConfig jobConfig) {
        mapper.insertJobConfig(jobConfig);
    }

    public void updateJobConfig(JobConfig jobConfig) {
        mapper.updateJobConfig(jobConfig);
    }

    public void delJobConfig(int id) {
        mapper.delJobConfig(id);
    }
    @Transactional(readOnly = true)
    public JobConfig queryJobConfigById(int id) {
        return mapper.queryJobConfigById(id);
    }
    @Transactional(readOnly = true)
    public List<JobConfig> queryAllJobs() {
        return mapper.queryAllJobs();
    }
}
