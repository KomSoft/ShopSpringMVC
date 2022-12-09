package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.model.CategoryModel;

import java.util.List;

public interface CategoryDAO {

    List<CategoryModel> getAllCategory() throws DataBaseException;
    CategoryModel getCategory(String category) throws DataBaseException, ValidationException;

}
