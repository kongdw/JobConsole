package cn.com.cis.domain;

import java.io.Serializable;

/**
 * 数据库连接类型
 * @version 1.0 2015-07-14
 * @author kongdw
 */
public class DatabaseType implements Serializable {

    private static final long serialVersionUID = -9197478986394202990L;

    private String type;
    private String description;
    private String url;
    private String driver;
    private String testSql;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTestSql() {
        return testSql;
    }

    public void setTestSql(String testSql) {
        this.testSql = testSql;
    }
}
