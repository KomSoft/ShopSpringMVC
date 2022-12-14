package com.komsoft.shopspringmvc.service;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final DAOFactory daoFactory;

    @Autowired
    public CategoryService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public CategoryModel getById(String categoryId) throws ValidationException, DataBaseException {
        CategoryModel category = null;
        if (categoryId == null) {
            throw new ValidationException("Oooops! <br>Category Id isn't present");
        }
        try {
            long id = Long.parseLong(categoryId);
            category = daoFactory.getCategoryDAO().getById(id);
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid category id: %s, try another", categoryId));
        }
        return category;
    }

    public List<CategoryModel> getAll() throws DataBaseException {
        List<CategoryModel> categories = daoFactory.getCategoryDAO().getAll();
        return categories;
    }

}
