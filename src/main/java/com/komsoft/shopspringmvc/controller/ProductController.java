package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.service.ProductService;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllProduct(ModelMap modelMap, @RequestParam (required = false) String category,
                                @RequestParam(required = false, name = "product") String productId) {
        String view;
        List<ProductDto> products = new ArrayList<>();
        try {
            if (productId != null) {
                ProductDto productDto = productService.getById(productId);
                if (productDto == null) {
                    throw new DataBaseException(String.format("No product(s) found for id=%s", productId));
                }
                products.add(productDto);
                view = Header.PRODUCT_INFO_PAGE;
            } else {
                if (category != null) {
                    products = productService.getProductByCategory(category);
                    if (products == null) {
                        throw new DataBaseException(String.format("No product(s) found for category=%s", category));
                    }
                } else {
                    products = productService.getAll();
                    if (products == null) {
                        throw new DataBaseException("Sorry, no product(s) found");
                    }
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
