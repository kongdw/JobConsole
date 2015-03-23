package cn.com.cis.task.entity;

import cn.com.cis.domain.ParameterInfo;
import cn.com.cis.enums.Type;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

public class TaskInfo implements Serializable {

    private static final long serialVersionUID = -1080304537013492176L;

    private String taskName;
    private String sqlScript;
    private DataSource source;
    private DataSource target;
    private String targetTable;
    private Type type;
    //private Map<String, ParameterInfo> parameterEntityMap;
    private List<ParameterInfo> parameterInfoList;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public DataSource getTarget() {
        return target;
    }

    public void setTarget(DataSource target) {
        this.target = target;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

//    public Map<String, ParameterInfo> getParameterEntityMap() {
//        return parameterEntityMap;
//    }
//
//    public void setParameterEntityMap(Map<String, ParameterInfo> parameterEntityMap) {
//        this.parameterEntityMap = parameterEntityMap;
//    }

    public List<ParameterInfo> getParameterInfoList() {
        return parameterInfoList;
    }

    public void setParameterInfoList(List<ParameterInfo> parameterInfoList) {
        this.parameterInfoList = parameterInfoList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
