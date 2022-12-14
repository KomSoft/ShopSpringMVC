package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.service.UserService;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/login")
public class BlockedLoginController {
    final int TIME_OUT = 10;
    final int MAX_TRY = 3;
    private int count = 0;
    private LocalDateTime endTime = null;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
    private final UserService userService;

    public BlockedLoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(ModelMap modelMap, HttpSession session, @RequestParam String login, @RequestParam String password) {
        String view = "";
        if (endTime != null) {
            long waitSecond = TIME_OUT - Duration.between(endTime, LocalDateTime.now()).getSeconds();
            if (waitSecond <= 0) {
                count = -1;
                endTime = null;
            } else {
                view = Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("waitForTimeout"), waitSecond));
            }
        }
        if (count < MAX_TRY) {
            if (login != null && login.trim().length() != 0) {      // isBlank() is not supported
                try {
                    String fullName = userService.getFullName(login, password);
                    if (fullName != null) {
                        modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("accessGranted"), fullName));
                        session.setAttribute(Header.AUTHENTICATED_USER_KEY, fullName);
                        view = Header.LOGIN_SUCCESS_PAGE;
                    } else {
                        count++;
                        if (count < MAX_TRY) {
                            if (count > 0) {
                                modelMap.addAttribute(Header.LOGIN_MESSAGE, String.format(bundle.getString("accessDenied"), (MAX_TRY - count)));
                            }
                            view = Header.LOGIN_PAGE;
                        } else {
                            endTime = LocalDateTime.now();
                            modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("blockedForTimeout"), TIME_OUT));
                            view = Header.ERROR_PAGE;
                        }
                    }
                } catch (DataBaseException e) {
                    modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
                    view = Header.ERROR_PAGE;
                }
            }
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginForm(HttpSession session) {
//        HttpSession session = request.getSession();
        String view = Header.LOGIN_PAGE;
//      if user is authenticated - show Welcome else show login form
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName from doPost()
            view = Header.LOGIN_SUCCESS_PAGE;
        }
            return view;
    }

}
