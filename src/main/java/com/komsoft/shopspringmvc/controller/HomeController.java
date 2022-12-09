package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
//@RequestMapping(value = "/")
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    @RequestMapping(method = RequestMethod.GET)
    public String goHome() {
//        String url = Header.PAGE_ROOT + "home.jsp";
//        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
//        dispatcher.forward(request, response);
        return Header.HOME_PAGE;
    }

/*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }
*/
}
