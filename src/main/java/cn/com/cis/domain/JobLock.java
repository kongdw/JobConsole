package cn.com.cis.domain;

import java.io.Serializable;
import java.util.Date;

public class JobLock implements Serializable {

    private static final long serialVersionUID = 3545190910765789995L;
    private int id;
    private int jobId;
    private String status;
    private Date lockDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }
}
