package com.komsoft.shopspringmvc.factory;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.repository.*;
import com.komsoft.shopspringmvc.util.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariInternalPostgresDAOFactory extends DAOFactory {

    private HikariConfig config = new HikariConfig();
//    private static HikariConfig config = new HikariConfig();
    private HikariDataSource hikariDataSources;
//    private static HikariDataSource hikariDataSources;
//    private static HikariInternalPostgresDAOFactory instance;

/*
    public Connection getConnection() throws DataBaseException {
        return getConnect();
    }
*/

//    public static Connection getConnect() throws DataBaseException {
    public Connection getConnection() throws DataBaseException {
 System.out.println("HikariInternalPostgresDAOFactory.getConnection() called");
        try {
            return hikariDataSources.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
    }

    public HikariInternalPostgresDAOFactory() throws DataBaseException {
//    private HikariInternalPostgresDAOFactory() throws DataBaseException {
 System.out.println("Constructor HikariInternalPostgresDAOFactory() called");
        DBProperties prop = new DBProperties("hikaripostgres");
        if (prop.isReady()) {
            config.setJdbcUrl(prop.getUrl());
            config.setUsername(prop.getUserName());
            config.setPassword(prop.getPassword());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariDataSources = new HikariDataSource(config);
        } else {
            throw new DataBaseException("Can't read DB properties. DB isn't connected");
        }
    }

/*
    public static HikariInternalPostgresDAOFactory getInstance() {
        if (instance == null) {
            try {
                instance = new HikariInternalPostgresDAOFactory();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
*/

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOPsqlRepositoryImpl(this);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAOPsqlRepositoryImpl(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOPsqlRepositoryImpl(this);
    }
}
