package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping(method = RequestMethod.GET)
    public String doLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession(true);
        return Header.LOGIN_PAGE;
    }

}
