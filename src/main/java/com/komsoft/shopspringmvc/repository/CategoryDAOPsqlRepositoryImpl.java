package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.CategoryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOPsqlRepositoryImpl implements CategoryDAO {
    private static final String GET_ALL_CATEGORY = "SELECT * FROM category ORDER BY id ASC";
    private static final String GET_ALL_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
    private DAOFactory daoFactory;

    public CategoryDAOPsqlRepositoryImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<CategoryModel> getAll() throws DataBaseException {
        List<CategoryModel> result = null;
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORY);
            ResultSet categories = statement.executeQuery();
            result = new ArrayList<>();
            while (categories.next()) {
                result.add(new CategoryModel()
                        .setId(categories.getLong("id"))
                        .setName(categories.getString("name")));
            }
            categories.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            daoFactory.closeConnection();
        }
        return result;
    }

    public CategoryModel getById(long category) throws DataBaseException {
        CategoryModel result = null;
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORY_BY_ID);
            statement.setLong(1, category);
            ResultSet categories = statement.executeQuery();
            result = new CategoryModel();
            if (categories.next()) {
                result = new CategoryModel()
                        .setId(categories.getLong("id"))
                        .setName(categories.getString("name"));
            } else {
                throw new DataBaseException(String.format("Oooops! <br>Category: %d not exists", category));
            }
            categories.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            daoFactory.closeConnection();
        }
        return result;
    }

}
