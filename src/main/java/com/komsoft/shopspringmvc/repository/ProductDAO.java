package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.model.ProductModel;

import java.util.List;

public interface ProductDAO {

    List<ProductModel> getAll() throws DataBaseException;
    ProductModel getById(long id) throws DataBaseException, ValidationException;
    List<ProductModel> getByCategory(long category) throws DataBaseException, ValidationException;

}
