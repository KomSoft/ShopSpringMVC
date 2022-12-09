package com.komsoft.shopspringmvc.factory;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.repository.CategoryDAO;
import com.komsoft.shopspringmvc.repository.ProductDAO;
import com.komsoft.shopspringmvc.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public abstract class DAOFactory {
    public static final int POSTGRESQL = 1;
    public static final int HIKARI_POSTGRESQL = 2;
    public abstract CategoryDAO getCategoryDAO();
    public abstract ProductDAO getProductDAO();
    public abstract UserDAO getUserDAO();
    public abstract Connection getConnection() throws DataBaseException;
    public abstract void closeConnection();
//    private DAOFactory instance;

    @Autowired
    public DAOFactory() {
    }

/*
    public DAOFactory getInstance(int whichFactory) {
//    public static DAOFactory getInstance(int whichFactory) {
        try {
            switch (whichFactory) {
                case POSTGRESQL: return new PostgreSQLDAOFactory();
                case HIKARI_POSTGRESQL: return new HikariInternalPostgresDAOFactory();
//                case HIKARI_POSTGRESQL: return new HikariInternal2PostgresDAOFactory();
//                case HIKARI_POSTGRESQL: return new HikariExternalPostgresDAOFactory();
//                case HIKARI_POSTGRESQL: return HikariCPPostgreSQLDAOFactory.getInstance();
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return null;
    }

*/
    public static DAOFactory getInstance() {
//    public static DAOFactory getInstance() {
        int whichFactory = -1;
        String datasource;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            datasource = bundle.getString( "datasource");
            if (datasource.equalsIgnoreCase("postgresql")) {
                return new PostgreSQLDAOFactory();
            }
            if (datasource.equalsIgnoreCase("hikari_postgresql")) {
                return new HikariInternalPostgresDAOFactory();
            }
        } catch (NullPointerException | MissingResourceException | ClassCastException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
