package cn.com.cis.domain;

import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

/**
 * 参数实体类
 */
public class ParameterInfo implements Serializable {

    private static final long serialVersionUID = 2703207349012466390L;

    private int paramIndex;
    private String paramName;
    private Object paramVal;
    private JdbcType paramType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getParamVal() {
        return paramVal;
    }

    public void setParamVal(Object paramVal) {
        this.paramVal = paramVal;
    }

    public JdbcType getParamType() {
        return paramType;
    }

    public void setParamType(JdbcType paramType) {
        this.paramType = paramType;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }
}
