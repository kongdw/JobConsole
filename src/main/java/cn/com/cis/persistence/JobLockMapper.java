package cn.com.cis.persistence;

import cn.com.cis.domain.JobLock;

import java.util.List;

public interface JobLockMapper {

    void insertJobLock(JobLock jobEntityLock);
    void deleteJobLock(int id);
    void deleteJobLockByJobId(int jobId);
    JobLock selectJobLockById(int id);
    List<JobLock> selectAllJobEntityLock();
    JobLock selectJobLockByJobId(int jobId);
    void updateJobLock(JobLock jobEntityLock);
}
