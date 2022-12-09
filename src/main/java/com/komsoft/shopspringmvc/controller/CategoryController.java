package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.CategoryModel;
import com.komsoft.shopspringmvc.repository.CategoryDAO;
import com.komsoft.shopspringmvc.util.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final DAOFactory daoFactory;

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CategoryController() {
        this.daoFactory = DAOFactory.getInstance();
    }

    //    @Override
//    protected void doGet(HttpSession session) {
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public void getAllCategory(HttpSession session) {
        List<CategoryModel> categories = null;
//        List<CategoryModel> categories = new ArrayList<>();
        try {
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            categories = categoryDAO.getAllCategory();
        } catch (DataBaseException e) {
//      TODO - поки так
            logger.error(String.format("[CategoryController] {]}", e.getMessage()));
//        } finally {
/*
            if (categories.size() == 0) {
                categories.add(new Category().setId(0L).setName("DB Error"));
            }
*/
        }
//        logger.info(String.format("[CategoryController] categories.size={}, categories={}", categories.size(), categories.toString()));
        session.setAttribute(Header.CATEGORIES, categories);
    }

}
