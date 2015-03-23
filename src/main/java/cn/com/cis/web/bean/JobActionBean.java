package cn.com.cis.web.bean;

public class JobActionBean {

    private int jobId;
    private String jobName;
    private int sequence;
    private String modeCode;
    private int layerId;
    private int sourceDatabaseId;
    private int targetDatabaseId;
    private String targetTable;
    private String sqlScript;
    private String description;
    private int splitValue;
    private boolean truncateFlag;
    private String sqlType;

    public String getSqlType() {
        return sqlType;
    }
    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getModeCode() {
        return modeCode;
    }

    public void setModeCode(String modeCode) {
        this.modeCode = modeCode;
    }

    public int getLayerId() {
        return layerId;
    }

    public void setLayerId(int layerId) {
        this.layerId = layerId;
    }

    public int getSourceDatabaseId() {
        return sourceDatabaseId;
    }

    public void setSourceDatabaseId(int sourceDatabaseId) {
        this.sourceDatabaseId = sourceDatabaseId;
    }

    public int getTargetDatabaseId() {
        return targetDatabaseId;
    }

    public void setTargetDatabaseId(int targetDatabaseId) {
        this.targetDatabaseId = targetDatabaseId;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public int getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(int splitValue) {
        this.splitValue = splitValue;
    }

    public boolean isTruncateFlag() {
        return truncateFlag;
    }

    public void setTruncateFlag(boolean truncateFlag) {
        this.truncateFlag = truncateFlag;
    }
}
