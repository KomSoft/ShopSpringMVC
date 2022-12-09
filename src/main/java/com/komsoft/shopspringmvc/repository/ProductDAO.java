package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;

import java.util.List;

public interface ProductDAO {

    List<ProductDto> getAllProduct(String category) throws DataBaseException, ValidationException;
    ProductDto getProductById(String id) throws DataBaseException, ValidationException;

}
