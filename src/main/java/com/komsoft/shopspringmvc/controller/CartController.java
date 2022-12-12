package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.dto.ProductDto;
import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.ProductModel;
import com.komsoft.shopspringmvc.repository.ProductDAO;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final DAOFactory daoFactory;

    public CartController() {
        this.daoFactory = DAOFactory.getInstance();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String showCart(ModelMap modelMap, HttpSession session) {
        String view;
        Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
        if (cart == null || cart.size() == 0) {
            view = Header.INFO_PAGE;
            modelMap.addAttribute(Header.MESSAGE, "Your Cart is empty. First put items in.");
        } else {
            view = Header.CART_PAGE;
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public String addProduct(ModelMap modelMap, HttpServletRequest request, HttpSession session,
                             @RequestParam(name = "id") String productId, @RequestParam(name = "count") String productQuantity) {
        String view = "redirect:" + request.getHeader("referer");
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
// TODO !!!!!!!!!!!
            view = Header.INFO_PAGE;
//            modelMap.addAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
            modelMap.addAttribute(Header.MESSAGE, "To put items into Cart please login first");
        } else {
            try {
                int count = productQuantity == null ? 0 : Integer.parseInt(productQuantity);
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
                view = "redirect:" + request.getHeader("referer");
            } catch (NumberFormatException ignored) {
//            because  <input type="number"
            } catch (ValidationException | DataBaseException e) {
                view = Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, e.getMessage());
            }
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String deleteProduct(ModelMap modelMap, HttpSession session,
                                @RequestParam(name = "id") String productId) {
        String view;
        try {
            if (session.getAttribute(Header.USER_CART) != null) {
                Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
                ProductDto product = daoFactory.getProductDAO().getProductById(productId);
                if (product != null) {
                    cart.remove(product);
                }
                session.setAttribute(Header.USER_CART, cart);
            }
            view = Header.CART_PAGE;
        } catch (ValidationException | DataBaseException e) {
            view = Header.ERROR_PAGE;
            modelMap.addAttribute(Header.MESSAGE, e.getMessage());
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(ModelMap modelMap, HttpSession session) {
        return showCart(modelMap, session);
    }

}
