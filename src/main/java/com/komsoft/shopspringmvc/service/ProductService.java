package com.komsoft.shopspringmvc.service;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.repository.ProductDAO;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final DAOFactory daoFactory;

    @Autowired
    public ProductService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getProductByCategoryOrId(ModelMap modelMap, String category, String id) {
        String view;
        List<ProductDto> products = new ArrayList<>();
        try {
            ProductDAO productDAO = daoFactory.getProductDAO();
            if (id != null) {
                ProductDto productDto = productDAO.getProductById(id);
                if (productDto == null) {
                    throw new DataBaseException(String.format("No product(s) found for id=%s", id));
                }
                products.add(productDto);
                view = Header.PRODUCT_INFO_PAGE;
            } else {
                products = productDAO.getAllProduct(category);
                if (products.size() == 0) {
                    String error = "Sorry, no product(s) found";
                    if (category != null) {
                        error = String.format("No product(s) found for category=%s", category);
                    }
                    throw new DataBaseException(error);
                }
                view = Header.PRODUCTS_PAGE;
            }
            modelMap.addAttribute("products", products);
        } catch (DataBaseException | ValidationException e) {
            view = Header.ERROR_PAGE;
            modelMap.addAttribute(Header.MESSAGE, e.getMessage());
        }
        return view;
    }

}
