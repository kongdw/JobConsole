package cn.com.cis.web.bean;

public class ParameterActionBean {

    private String parameterRef;
    private String parameterName;
    private String parameterType;
    private String parameterValue;
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getParameterRef() {
        return parameterRef;
    }

    public void setParameterRef(String parameterRef) {
        this.parameterRef = parameterRef;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
