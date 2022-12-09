package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pay")
public class PayController {

/*
    @Override
    public void init() throws ServletException {
        super.init();
    }
*/

/*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
    }
*/

    @RequestMapping(method = RequestMethod.POST)
    public String doPay(ModelMap modelMap, HttpServletRequest request) {
//    protected void doPost(ModelMap modelMap, HttpServletRequest request) {
//        RequestDispatcher dispatcher;
//        String url;
        String view = Header.PAY_PAGE;
//        url = Header.PAGE_ROOT + Header.PAY_PAGE;
        modelMap.addAttribute(Header.PAY_MESSAGE, "You can pay your items on this page");
//        request.getSession().setAttribute(Header.PAY_MESSAGE, "You can pay your items on this page");
//        dispatcher = request.getRequestDispatcher(url);
//        dispatcher.forward(request, response);
        return view;
    }

}
