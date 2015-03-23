package cn.com.cis.service;

import cn.com.cis.domain.JobLog;
import cn.com.cis.persistence.JobLogMapper;
import cn.com.cis.plugins.mybatis.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobLogService {

    @Autowired
    private JobLogMapper mapper;


    public void insertJobEntityLog(JobLog jobExecuteLog) {
        mapper.insertJobLog(jobExecuteLog);
    }

    public void updateJobEntityLog(JobLog jobExecuteLog) {
        mapper.updateJobLog(jobExecuteLog);
    }

    @Transactional(readOnly = true)
    public List<JobLog> selectJobEntityLogByJobName(String jobName) {
        return mapper.selectJobLogByJobName(jobName);
    }

    @Transactional(readOnly = true)
    public JobLog selectJobEntityLogById(int id) {
        return mapper.selectJobLogById(id);
    }

    public void append(int id, String message) {
        JobLog jobEntityLog = mapper.selectJobLogById(id);
        jobEntityLog.setMessage(jobEntityLog.getMessage() + "\n" + message);
        mapper.updateJobLog(jobEntityLog);
    }

    public List<JobLog> selectAllJobLog(int page,int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectAllJobLog();
    }

    public List<JobLog> selectAllJobLogMain() {
        return mapper.selectAllJobLog();
    }

}
