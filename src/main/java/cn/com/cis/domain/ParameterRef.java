package cn.com.cis.domain;

import java.io.Serializable;

public class ParameterRef implements Serializable{
    private static final long serialVersionUID = 5305059245259061333L;

    private int id;
    private String refName;
    private Database database;
    private String sqlScript;
    private ParameterType returnType;
    private String defaultVal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public ParameterType getReturnType() {
        return returnType;
    }

    public void setReturnType(ParameterType returnType) {
        this.returnType = returnType;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }
}
