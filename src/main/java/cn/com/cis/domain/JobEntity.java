package cn.com.cis.domain;

import java.io.Serializable;

public class JobEntity implements Serializable {

    private static final long serialVersionUID = -7064891016378225064L;
    private int id;
    private Layer layer;
    private Mode mode;
    private int sequence;
    private String name;
    private String sqlScript;
    private String sqlType;
    private String targetTable;
    private Database sourceDatabase;
    private Database targetDatabase;
    private boolean truncateFlag;
    private boolean enabled;
    private String description;
    private int splitValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public Database getSourceDatabase() {
        return sourceDatabase;
    }

    public void setSourceDatabase(Database sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public Database getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(Database targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTruncateFlag() {
        return truncateFlag;
    }

    public void setTruncateFlag(boolean truncateFlag) {
        this.truncateFlag = truncateFlag;
    }

    public int getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(int splitValue) {
        this.splitValue = splitValue;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }
}
