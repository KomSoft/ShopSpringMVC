package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.model.ProductModel;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductEntityManager implements ProductDAO {
    EntityManager entityManager = Persistence.createEntityManagerFactory("shopspringmvc").createEntityManager();

    @Override
    public List<ProductModel> getAll()  throws DataBaseException {
        TypedQuery<ProductModel> query = entityManager.createNamedQuery("Product.getAll", ProductModel.class);
        return query.getResultList();
    }

    @Override
    public ProductModel getById(long id) {
        return entityManager.find(ProductModel.class, id);
    }

    @Override
    public List<ProductModel> getByCategory(long categoryId) {
        TypedQuery<ProductModel> query = entityManager.createNamedQuery("Product.getByCategory", ProductModel.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }


}
