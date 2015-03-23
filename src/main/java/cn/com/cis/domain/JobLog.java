package cn.com.cis.domain;

import cn.com.cis.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

public class JobLog implements Serializable {

    private static final long serialVersionUID = 5167989761646090688L;
    private int id;
    private Date logDate;
    private String jobName;
    private String username;
    private long nbLine;
    private Date beginTime;
    private Date endTime;
    private String status;
    private String message = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getNbLine() {
        return nbLine;
    }

    public void setNbLine(long nbLine) {
        this.nbLine = nbLine;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        message = message.replace("\n","<br>").replace("\"","");
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String appendMessage(String message) {
        this.message = this.message + "\n" + message;
        return this.message;
    }
}

