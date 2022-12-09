package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.ProductModel;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final DAOFactory daoFactory;

    public CartController() {
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
    @RequestMapping(method = RequestMethod.POST)
    public String showCart(ModelMap modelMap, HttpSession session) {
//    protected void doGet(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher;
//        String url;
        String view;
        Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
//        Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) request.getSession().getAttribute(Header.USER_CART);
        if (cart == null || cart.size() == 0) {
            view = Header.INFO_PAGE;
//            url = Header.PAGE_ROOT + Header.INFO_PAGE;
            modelMap.addAttribute(Header.MESSAGE, "Your Cart is empty. First put items in.");
//            request.getSession().setAttribute(Header.MESSAGE, "Your Cart is empty. First put items in.");
        } else {
            view = Header.CART_PAGE;
//            url = Header.PAGE_ROOT + Header.CART_PAGE;
        }
        return view;
/*
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
*/
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String addProduct(ModelMap modelMap, HttpServletRequest request, HttpSession session,
                             @RequestParam(name = "id") String productId, @RequestParam(name = "count") String productQuantity) {
//        RequestDispatcher dispatcher;
        String view = request.getHeader("Referer");
//        String url;
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
//        if (request.getSession().getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
//           user not logged in - redirect to login page
//            url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
            view = Header.LOGIN_PAGE;
//            dispatcher = request.getRequestDispatcher(url);
            modelMap.addAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
//            request.setAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
//            dispatcher.forward(request, response);
//            return view;
        } else {
//            String idString = request.getParameter("id");
//        String countString = request.getParameter("count");
//        boolean isDelete = request.getParameter("delete") != null;
            try {
                int count = productQuantity == null ? 0 : Integer.parseInt(productQuantity);
//            HttpSession session = request.getSession();
                if (session.getAttribute(Header.USER_CART) == null) {
                    session.setAttribute(Header.USER_CART, new HashMap<ProductModel, Integer>());
                }
                Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
                ProductDAO productDAO = daoFactory.getProductDAO();
                ProductDto product = productDAO.getProductById(productId);
                if (product != null) {
                    int quantity = cart.get(product) == null ? 0 : cart.get(product);
                    quantity += count;
                    cart.put(product, quantity);
                }
                session.setAttribute(Header.USER_CART, cart);
//                response.sendRedirect(request.getHeader("Referer"));
//  TODO - sendRedirect(request.getHeader("Referer"));
                view = request.getHeader("Referer"); // ?? for what page - referer
            } catch (NumberFormatException ignored) {
//            because  <input type="number"
            } catch (ValidationException | DataBaseException e) {
                view = Header.ERROR_PAGE;
//                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, e.getMessage());
//                request.getSession().setAttribute(Header.MESSAGE, e.getMessage());
//                dispatcher = request.getRequestDispatcher(url);
//                dispatcher.forward(request, response);
            }
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public String deleteProduct(ModelMap modelMap, HttpSession session,
                                @RequestParam(name = "id") String productId) {
//        RequestDispatcher dispatcher;
        String view;
//        String url;
//        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
//        if (request.getSession().getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
//           user not logged in - redirect to login page
//            url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
//            view = Header.LOGIN_PAGE;
//            dispatcher = request.getRequestDispatcher(url);
            modelMap.addAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
//            request.setAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
//            dispatcher.forward(request, response);
//            return view;
//        }
//        String idString = request.getParameter("id");
//        int productId = request.getParameter("id");
//        String countString = request.getParameter("count");
//        boolean isDelete = request.getParameter("delete") != null;
        try {
//            int count = countString == null ? 0 : Integer.parseInt(countString);
//            HttpSession session = request.getSession();
            if (session.getAttribute(Header.USER_CART) != null) {
//                session.setAttribute(Header.USER_CART, new HashMap<ProductModel, Integer>());
//            }
                Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
//            ProductDAO productDAO = daoFactory.getProductDAO();
                ProductDto product = daoFactory.getProductDAO().getProductById(productId);
//                ProductDto product = daoFactory.getProductDAO().getProductById(idString);
                if (product != null) {
//                if (isDelete) {
                    cart.remove(product);
//                } else {
//                    int quantity = cart.get(product) == null ? 0 : cart.get(product);
//                    quantity += count;
//                    cart.put(product, quantity);
//                }
                }
                session.setAttribute(Header.USER_CART, cart);
            }
//            response.sendRedirect(request.getHeader("Referer"));
            view = Header.CART_PAGE;
//        } catch (NumberFormatException ignored) {
//            because  <input type="number"
        } catch (ValidationException | DataBaseException e) {
//            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
            view = Header.ERROR_PAGE;
//            request.getSession().setAttribute(Header.MESSAGE, e.getMessage());
            session.setAttribute(Header.MESSAGE, e.getMessage());
//            dispatcher = request.getRequestDispatcher(url);
//            dispatcher.forward(request, response);
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(ModelMap modelMap, HttpSession session) {
        return showCart(modelMap, session);
    }

}
