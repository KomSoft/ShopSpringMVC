package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.repository.ProductDAO;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final DAOFactory daoFactory;

    public ProductController() {
        this.daoFactory = DAOFactory.getInstance();
    }

/*
    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }
*/

//    @Override
//   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    @RequestMapping(method = RequestMethod.POST)
    public String getProduct(ModelMap modelMap, @RequestParam String category, @RequestParam(name = "product") String productId) {
//        RequestDispatcher dispatcher;
        String view;
//        String url;
//        String category = request.getParameter("category");
//        String productId = request.getParameter("product");
        List<ProductDto> products = new ArrayList<>();
        try {
            ProductDAO productDAO = daoFactory.getProductDAO();
            if (productId != null) {
                 ProductDto productDto = productDAO.getProductById(productId);
                if (productDto == null) {
                    throw new DataBaseException(String.format("No product(s) found for id=%s", productId));
                }
                products.add(productDto);
                view = Header.PRODUCT_INFO_PAGE;
//                url = Header.PAGE_ROOT + "productinfo.jsp";
            } else {
                products = productDAO.getAllProduct(category);
                if (products.size() == 0) {
                    throw new DataBaseException(String.format("No product(s) found for category=%s", category));
                }
                view = Header.PRODUCTS_PAGE;
//                url = Header.PAGE_ROOT + "products.jsp";
            }
            modelMap.addAttribute("products", products);
//            request.setAttribute("products", products);
        } catch (DataBaseException | ValidationException e) {
            view = Header.ERROR_PAGE;
//            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
            modelMap.addAttribute(Header.MESSAGE, e.getMessage());
//            request.getSession().setAttribute(Header.MESSAGE, e.getMessage());
        }
//        dispatcher = request.getRequestDispatcher(url);
//        dispatcher.forward(request, response);
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllProduct(ModelMap modelMap) {
        String view = Header.PRODUCTS_PAGE;
        List<ProductDto> products = new ArrayList<>();
        try {
            ProductDAO productDAO = daoFactory.getProductDAO();
            products = productDAO.getAllProduct(null);
            if (products.size() == 0) {
                throw new DataBaseException("Sorry, no product(s) found");
            }
            modelMap.addAttribute("products", products);
        } catch (ValidationException | DataBaseException e) {
            view = Header.ERROR_PAGE;
            modelMap.addAttribute(Header.MESSAGE, e.getMessage());
        }
        return view;
    }
/*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }
*/

}
