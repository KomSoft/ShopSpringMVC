package com.komsoft.shopspringmvc.service;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final DAOFactory daoFactory;

    @Autowired
    public ProductService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public ProductDto getById(String productId) throws ValidationException, DataBaseException {
        ProductDto productDto = null;
        if (productId == null) {
            throw new ValidationException("Oooops! <br>Product Id isn't present");
        }
        try {
            long id = Long.parseLong(productId);
            ProductModel product = daoFactory.getProductDAO().getById(id);
            productDto = new ProductConverter().convertProductToDto(product);
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid product id: %s, try another", productId));
        }
        return productDto;
    }

    public List<ProductDto> getProductByCategory(String categoryId) throws ValidationException, DataBaseException {
        List<ProductDto> productsDto = new ArrayList<>();
        if (categoryId == null) {
            throw new ValidationException("Oooops! <br>Category Id isn't present");
        }
        try {
            long id = Long.parseLong(categoryId);
            List<ProductModel> products = daoFactory.getProductDAO().getByCategory(id);
            for (ProductModel product : products) {
                productsDto.add(new ProductConverter().convertProductToDto(product));
            }
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid category Id: %s, try another", categoryId));
        }
        return productsDto;
    }

    public List<ProductDto> getAll() throws DataBaseException {
        List<ProductDto> productsDto = new ArrayList<>();
        List<ProductModel> products = daoFactory.getProductDAO().getAll();
        for (ProductModel product : products) {
            productsDto.add(new ProductConverter().convertProductToDto(product));
        }
        return productsDto;
    }

}
