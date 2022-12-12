package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

/*
    @RequestMapping(method = RequestMethod.POST)
    public String getProduct(ModelMap modelMap, @RequestParam String category, @RequestParam(name = "product") String productId) {
        String view = productService.getProductByCategoryOrId(modelMap, category, productId);
        return view;
    }

*/
    @RequestMapping(method = RequestMethod.GET)
    public String getAllProduct(ModelMap modelMap, @RequestParam (required = false) String category,
                                @RequestParam(required = false, name = "product") String productId) {
        String view = productService.getProductByCategoryOrId(modelMap, category, productId);
        return view;
    }


}
