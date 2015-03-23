package cn.com.cis.utils;

import cn.com.cis.domain.Database;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * DataSource 静态工厂类，支持延时加载
 *
 * @author kongdw
 * @version 1.0
 *
 */
public class DataSourceFactory {

    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    public synchronized static  DataSource getDataSource(Database database) {
        DataSource dataSource = null;
        if (database != null) {
            try {
                if (dataSourceMap.containsKey(database.getName())) {
                    dataSource = dataSourceMap.get(database.getName());
                } else {
                    dataSource = createDataSource(database);
                    dataSourceMap.put(database.getName(), dataSource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataSource;
        }else {
            return null;
        }
    }

    private static DataSource createDataSource(Database database) {
        PooledDataSource dataSource = new PooledDataSource();
        String url = database.getDatabaseType().getUrl()
                .replace("<host>", database.getHostName())
                .replace("<port>", String.valueOf(database.getPort()))
                .replace("<serverName>", database.getServerName());
        dataSource.setDefaultAutoCommit(false);
        dataSource.setDriver(database.getDatabaseType().getDriver());
        dataSource.setUrl(url);
        dataSource.setUsername(database.getUsername());
        dataSource.setPassword(database.getPassword());
        try {
            dataSource.setLoginTimeout(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setPoolMaximumCheckoutTime(50000);
        dataSource.setPoolMaximumActiveConnections(50);
        dataSource.setPoolMaximumIdleConnections(0);
        dataSource.setPoolPingEnabled(true);
        dataSource.setPoolPingQuery("select * from dual");
        return dataSource;
    }

    public synchronized static  void updateDataSource(Database database) {
        dataSourceMap.remove(database.getName());
        dataSourceMap.put(database.getName(), createDataSource(database));
    }
}
