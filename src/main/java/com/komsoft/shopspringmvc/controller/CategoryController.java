package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.model.CategoryModel;
import com.komsoft.shopspringmvc.service.CategoryService;
import com.komsoft.shopspringmvc.util.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getall")
    public String getAllCategory(HttpServletRequest request) {
        List<CategoryModel> categories = null;
        String view = "redirect:" + request.getHeader("referer");
        try {
            categories = categoryService.getAll();
        } catch (DataBaseException e) {
//      TODO - поки так
            logger.error("[CategoryController] {}", e.getMessage());
        }
        request.getSession().setAttribute(Header.CATEGORIES, categories);
        return view;
    }

}
