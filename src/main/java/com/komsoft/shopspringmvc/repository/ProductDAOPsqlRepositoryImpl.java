package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.CategoryModel;
import com.komsoft.shopspringmvc.model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsqlRepositoryImpl implements ProductDAO {
    private static final String GET_ALL_PRODUCT = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id";
    private static final String GET_ALL_PRODUCT_BY_CATEGORY = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id WHERE category.id=?";
    private static final String GET_PRODUCT_BY_ID = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id WHERE product.id=?";
    private DAOFactory daoFactory;

    public ProductDAOPsqlRepositoryImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<ProductModel> getAll() throws DataBaseException {
        List<ProductModel> result;
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PRODUCT);
            ResultSet products = statement.executeQuery();
            result = new ArrayList<>();
            while (products.next()) {
                result.add(new ProductModel()
                        .setId(products.getLong("p_id"))
                        .setName(products.getString("p_name"))
                        .setDescription(products.getString("description"))
                        .setPrice(products.getDouble("price"))
                        .setCategory(new CategoryModel().setId(products.getLong("c_id"))
                        .setName(products.getString("c_name"))));
            }
            products.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            daoFactory.closeConnection();
        }
        return result;
    }

    public List<ProductModel> getByCategory(long category) throws DataBaseException {
        List<ProductModel> result;
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PRODUCT_BY_CATEGORY);
            statement.setInt(1, (int) category);
            ResultSet products = statement.executeQuery();
            result = new ArrayList<>();
            while (products.next()) {
                result.add(new ProductModel()
                        .setId(products.getLong("p_id"))
                        .setName(products.getString("p_name"))
                        .setDescription(products.getString("description"))
                        .setPrice(products.getDouble("price"))
                        .setCategory(new CategoryModel().setId(products.getLong("c_id"))
                        .setName(products.getString("c_name"))));
            }
            products.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            daoFactory.closeConnection();
        }
        return result;
    }

    public ProductModel getById(long id) throws DataBaseException {
        ProductModel product = null;
        try {
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_ID);
            statement.setLong(1, id);
            ResultSet products = statement.executeQuery();
            if (products.next()) {
                product = new ProductModel()
                        .setId(products.getLong("p_id"))
                        .setName(products.getString("p_name"))
                        .setDescription(products.getString("description"))
                        .setPrice(products.getDouble("price"))
                        .setCategory(new CategoryModel().setId(products.getLong("c_id"))
                        .setName(products.getString("c_name")));
            }
            products.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            daoFactory.closeConnection();
        }
        return product;
    }

}
