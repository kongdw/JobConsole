package cn.com.cis.service;

import cn.com.cis.domain.Database;
import cn.com.cis.domain.DatabaseType;
import cn.com.cis.persistence.DatabaseMapper;
import cn.com.cis.utils.DataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class DataBaseService {

    @Autowired
    private DatabaseMapper mapper;

    public void insertDatabase(Database database){
        DataSourceFactory.updateDataSource(database);
        mapper.insertDatabase(database);
    }

    public void updateDatabase(Database database){
        DataSourceFactory.updateDataSource(database);
        mapper.updateDatabase(database);
    }

    public void deleteDatabase(int id){
        mapper.deleteDatabase(id);
    }

    public void insertDatabaseType(DatabaseType databasetype){
        mapper.insertDatabaseType(databasetype);
    }

    public void updateDatabaseType(DatabaseType databasetype){
        mapper.updateDatabaseType(databasetype);


    }

    public void deleteDatabaseType(String type){
        mapper.deleteDatabaseType(type);
    }


    @Transactional(readOnly = true)
    public Database selectDatabaseById(int id){
        return mapper.selectDatabaseById(id);
    }

    @Transactional(readOnly = true)
    public List<DatabaseType> selectAllDatabaseType(){
        return mapper.selectAllDatabaseType();
    }
    @Transactional(readOnly = true)
    public DatabaseType selectDatabaseTypeById(String type){
        return mapper.selectDatabaseTypeById(type);
    }
    @Transactional(readOnly = true)
    public List<Database> selectAllDatabase(){
        return mapper.selectAllDatabase();
    }

    public Database selectDatabaseByName(String name){
        return mapper.selectDatabaseByName(name);
    }

    public boolean testDatabase(int id){
        Database database = mapper.selectDatabaseById(id);

        Connection connection;
        try {
            UnpooledDataSource dataSource = getDataSource(database);
            connection = dataSource.getConnection();
            return connection.isValid(3);
        } catch (SQLException e) {
            return false;
           // e.printStackTrace();
        } catch (ClassNotFoundException e) {
            return false;
            //e.printStackTrace();
        }
    }

    private UnpooledDataSource getDataSource(final Database database) throws ClassNotFoundException {
        String url = database.getDatabaseType().getUrl().replace("<host>", database.getHostName()).replace("<port>", String.valueOf(database.getPort())).replace("<serverName>", database.getServerName());
        return new UnpooledDataSource(database.getDatabaseType().getDriver(), url, database.getUsername(), database.getPassword());
    }

    public int selectDatabaseByType(String type){
        return mapper.selectDatabaseByType(type);
    }
}
