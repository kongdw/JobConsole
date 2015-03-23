package cn.com.cis.domain;

import java.io.Serializable;

/**
 *
 * 数据库链接信息实体类
 *
 */

public class Database implements Serializable {

    private static final long serialVersionUID = -4470281017887560469L;
    private int id;
    private String name;
    private String hostName;
    private int port;
    private DatabaseType databaseType;
    private String serverName;
    private String databaseName;
    private String username;
    private String password;

    private String testSql;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTestSql() {
        return testSql;
    }

    public void setTestSql(String testSql) {
        this.testSql = testSql;
    }

    @Override
    public boolean equals(Object obj) {
        Database database = null;
        if (obj == null) return false;
        if (!(obj instanceof Database))
            return false;
        else
            database = (Database) obj;
        if(this.getDatabaseName().equals(database.getDatabaseName())
                && this.getDatabaseType().equals(database.getDatabaseType())
                && this.getHostName().equals(database.getHostName())
                && this.getServerName().equals(database.getServerName())
                && this.getUsername().equals(database.getUsername())
                && this.getPassword().equals(database.getPassword())
                && this.getPort() == database.getPort())
            return true;
        return false;
    }
}
