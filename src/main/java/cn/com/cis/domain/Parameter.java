package cn.com.cis.domain;

import java.io.Serializable;

public class Parameter implements Serializable {

    private static final long serialVersionUID = -7119240713141493212L;
    // 作业参数ID
    private int id;
    // 作业ID
    private int jobId;
    // 参数索引
    private int parameterIndex;
    // 参数名称 #{}
    private String parameterName;
    // 参数JDBC类型 varchar number
    private ParameterType parameterType;

    private ParameterRef parameterRef;
    private String scope;
    // 参数格式 如 yyyyMMdd
    private Object parameterValue;

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

    public int getParameterIndex() {
        return parameterIndex;
    }

    public void setParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ParameterRef getParameterRef() {
        return parameterRef;
    }

    public void setParameterRef(ParameterRef parameterRef) {
        this.parameterRef = parameterRef;
    }
}
