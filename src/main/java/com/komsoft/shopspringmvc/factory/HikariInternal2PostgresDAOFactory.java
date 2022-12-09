package com.komsoft.shopspringmvc.factory;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.repository.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariInternal2PostgresDAOFactory extends DAOFactory {

    private static HikariConfig config = new HikariConfig("datasourcepostgres");
    private static HikariDataSource hikariDataSources;

    public HikariInternal2PostgresDAOFactory() throws DataBaseException {
  System.out.println("Constructor HikariInternal2PostgresDAOFactory() called");
        hikariDataSources = new HikariDataSource(config);
    }

    public Connection getConnection() throws DataBaseException {
 System.out.println("HikariInternal2PostgresDAOFactory.getConnection() called");
        try {
            return hikariDataSources.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
    }

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
