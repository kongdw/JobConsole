package cn.com.cis.persistence;

import cn.com.cis.domain.Database;
import cn.com.cis.domain.DatabaseType;

import java.util.List;


public interface DatabaseMapper {

    void insertDatabase(Database database);

    void updateDatabase(Database database);

    void deleteDatabase(int id);

    Database selectDatabaseById(int id);

    List<Database> selectAllDatabase();

    Database selectDatabaseByName(String name);

    void insertDatabaseType(DatabaseType databaseType);

    void updateDatabaseType(DatabaseType databaseType);

    void deleteDatabaseType(String type);

    List<DatabaseType> selectAllDatabaseType();

    DatabaseType selectDatabaseTypeById(String type);

    int selectDatabaseByType(String type);
}
