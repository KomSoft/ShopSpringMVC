package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.service.UserService;
import com.komsoft.shopspringmvc.util.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/login")
public class BlockedLoginController {
    //    @Serial
//    private static final long serialVersionUID = -6198900590472649299L;
    final int TIME_OUT = 10;
    final int MAX_TRY = 3;
    private int count = 0;
    private LocalDateTime endTime = null;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
    RequestDispatcher dispatcher = null;
//    DAOFactory daoFactory;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//    @Autowired
    private final UserService userService;

    public BlockedLoginController(UserService userService) {
        this.userService = userService;
    }

/*
    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }
*/

    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(ModelMap modelMap, HttpSession session, @RequestParam String login, @RequestParam String password) {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
        String view = "";
//        String url = "";
        if (endTime != null) {
            long waitSecond = TIME_OUT - Duration.between(endTime, LocalDateTime.now()).getSeconds();
            if (waitSecond <= 0) {
                count = -1;
                endTime = null;
            } else {
                view = Header.ERROR_PAGE;
//                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("waitForTimeout"), waitSecond));
//                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("waitForTimeout"), waitSecond));
            }
        }
        if (count < MAX_TRY) {
//            String login = request.getParameter("login");
//            String password = request.getParameter("password");
//            String fullName;
            if (login != null && login.trim().length() != 0) {      // isBlank() is not supported
                try {
                    String fullName = userService.getFullName(login, password);
/*
                    UserDAO userDAO = daoFactory.getUserDAO();
//                  without using BCrypt can use this way
//                    fullName = userRepository.getFullNameByLoginAndPassword(login, UserRegisteringData.encryptPassword(password));
//                    if (fullName != null) {
                    UserRegisteringDataModel user = userDAO.getUserByLogin(login);
//                    userRepository.closeConnection();
*/
/*
                    if (user != null && user.isPasswordCorrect(password)) {
                        fullName = user.getFullName();
*/
                    if (fullName != null) {
//                        request.setAttribute(Header.MESSAGE, String.format(bundle.getString("accessGranted"), fullName));
//                        request.getSession().setAttribute(Header.AUTHENTICATED_USER_KEY, fullName);
//                        url = Header.PAGE_ROOT + Header.LOGIN_SUCCESS_PAGE;
                        modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("accessGranted"), fullName));
                        session.setAttribute(Header.AUTHENTICATED_USER_KEY, fullName);
                        view = Header.LOGIN_SUCCESS_PAGE;
                    } else {
                        count++;
                        if (count < MAX_TRY) {
                            if (count > 0) {
//                                request.setAttribute(Header.LOGIN_MESSAGE, String.format(bundle.getString("accessDenied"), (MAX_TRY - count)));
                                modelMap.addAttribute(Header.LOGIN_MESSAGE, String.format(bundle.getString("accessDenied"), (MAX_TRY - count)));
                            }
//                            url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
                            view = Header.LOGIN_PAGE;
                        } else {
                            endTime = LocalDateTime.now();
//                            request.setAttribute(Header.MESSAGE, String.format(bundle.getString("blockedForTimeout"), TIME_OUT));
//                            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                            modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("blockedForTimeout"), TIME_OUT));
                            view = Header.ERROR_PAGE;
                        }
                    }
                } catch (DataBaseException e) {
//                    request.setAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
//                    url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                    modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
                    view = Header.ERROR_PAGE;
                }
            }
        }
        return view;
/*
        dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.getWriter().write(e.getMessage());
        }
*/
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginForm(HttpServletRequest request, @RequestParam(required = false) String logoutKey) {
//        response.setContentType("text/html");
        HttpSession session = request.getSession();
//      If exists Logout_Key parameter - close session (logout) and start new
//        if (Header.LOGOUT_KEY.equals(logoutKey)) {
//        if (request.getParameter(Header.LOGOUT_KEY) != null) {
//            session.invalidate();
//            session = request.getSession(true);
//        }
//        String url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
        String view = Header.LOGIN_PAGE;
//      if user is authenticated - show Welcome else show login form
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName from doPost()
            view = Header.LOGIN_SUCCESS_PAGE;
//            url = Header.PAGE_ROOT + Header.LOGIN_SUCCESS_PAGE;
        }
//        dispatcher = request.getRequestDispatcher(url);
//        try {
//            dispatcher.forward(request, response);
            return view;
//        } catch (ServletException e) {
//            response.getWriter().write(e.getMessage());
//        }
    }

}
