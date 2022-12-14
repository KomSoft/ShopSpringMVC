package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.model.CategoryModel;

import java.util.List;

public interface CategoryDAO {

    List<CategoryModel> getAll() throws DataBaseException;
    CategoryModel getById(long category) throws DataBaseException;

}
