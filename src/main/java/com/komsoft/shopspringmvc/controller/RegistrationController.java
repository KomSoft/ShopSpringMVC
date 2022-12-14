package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.model.UserAuthorizationData;
import com.komsoft.shopspringmvc.model.RegisteredUser;
import com.komsoft.shopspringmvc.service.UserService;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(ModelMap modelMap, HttpServletRequest request) {
        UserAuthorizationData checkedUser = new UserAuthorizationData(request);
        String view;
        if (checkedUser.isCorrect()) {
            try {
                RegisteredUser savedUser = userService.saveUser(checkedUser.getUserRegisteringData());
                view = Header.INFO_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("registerCompleted"), savedUser.getFullName()));
//          Register completed
            } catch (DataBaseException e) {
                view = Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
            } catch (ValidationException e) {
                view = Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("userIncorrectData"), e.getMessage()));
            }
        } else {
            view = Header.REGISTRATION_PAGE;
//  have to put in session to correct work
            request.getSession().setAttribute("errors", checkedUser.getErrors());
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String goRegistration(HttpSession session) {
        String view;
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName
            view = Header.LOGIN_WELCOME_PAGE;
        } else {
            view = Header.REGISTRATION_PAGE;
        }
        return view;
    }

}
