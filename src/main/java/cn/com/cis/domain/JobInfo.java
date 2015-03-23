package cn.com.cis.domain;

import cn.com.cis.enums.Model;
import cn.com.cis.enums.Type;

import java.io.Serializable;
import java.util.List;

/**
 * 后台作业实体类
 */
public class JobInfo implements Serializable{

    private static final long serialVersionUID = -8635218855399068067L;
    private int jobId;
    private String jobName;
    private String sqlScript;
    private Database sourceDatabase;
    private Database targetDatabase;
    private String targetTable;
    private boolean truncateFlag;
    private Model model;
    private Type type; // insert,update,delete
    //private Map<String,ParameterInfo> parameterEntityMap;

    private List<ParameterInfo> parameterInfoList;

    private String submitUser;

    private int splitVal;

    public int getSplitVal() {
        return splitVal;
    }

    public void setSplitVal(int splitVal) {
        this.splitVal = splitVal;
    }

    public List<ParameterInfo> getParameterInfoList() {
        return parameterInfoList;
    }

    public void setParameterInfoList(List<ParameterInfo> parameterInfoList) {
        this.parameterInfoList = parameterInfoList;
    }

    //    public Map<String, ParameterInfo> getParameterEntityMap() {
//        return parameterEntityMap;
//    }
//
//    public void setParameterEntityMap(Map<String, ParameterInfo> parameterEntityMap) {
//        this.parameterEntityMap = parameterEntityMap;
//    }

    public Database getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(Database targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    public Database getSourceDatabase() {
        return sourceDatabase;
    }

    public void setSourceDatabase(Database sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public boolean isTruncateFlag() {
        return truncateFlag;
    }

    public void setTruncateFlag(boolean truncateFlag) {
        this.truncateFlag = truncateFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof JobInfo))
            return false;
        JobInfo jobInfo = (JobInfo) obj;
        return this.getJobId() == jobInfo.getJobId();
    }
}
