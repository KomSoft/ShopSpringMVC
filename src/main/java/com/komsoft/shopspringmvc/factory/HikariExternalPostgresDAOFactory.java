package com.komsoft.shopspringmvc.factory;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.repository.*;

import java.sql.Connection;

public class HikariExternalPostgresDAOFactory extends DAOFactory {

    public HikariExternalPostgresDAOFactory() {

    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOPsqlRepositoryImpl(this);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOPsqlRepositoryImpl(this);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAOPsqlRepositoryImpl(this);
    }

    public Connection getConnection() throws DataBaseException {
        return HikariCP.getConnection();
    }

    public void closeConnection() {
    }

}
