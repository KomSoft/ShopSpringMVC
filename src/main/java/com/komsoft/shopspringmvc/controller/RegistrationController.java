package com.komsoft.shopspringmvc.controller;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.AuthorizedUserModel;
import com.komsoft.shopspringmvc.repository.UserDAO;
import com.komsoft.shopspringmvc.util.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    //    @Serial
//    private static final long serialVersionUID = -1210613704116541742L;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
//    RequestDispatcher dispatcher = null;
    private final DAOFactory daoFactory;

    public RegistrationController() {
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
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(ModelMap modelMap, HttpServletRequest request) {
        AuthorizedUserModel checkedUser = new AuthorizedUserModel(request);
        String view;
//        String url;
        if (checkedUser.isCorrect()) {
//            write to DB, check errors and get result
            try {
                UserDAO userDAO = daoFactory.getUserDAO();
                userDAO.saveUser(checkedUser.getUserRegisteringData());
                view = Header.INFO_PAGE;
//                url = Header.PAGE_ROOT + Header.INFO_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("registerCompleted"), checkedUser.getUserRegisteringData().getFullName()));
//                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("registerCompleted"), checkedUser.getUserRegisteringData().getFullName()));
//                can redirect to \login there but then we won't see a result
//          Register completed
            } catch (DataBaseException e) {
                view = Header.ERROR_PAGE;
//                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
//                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
            } catch (ValidationException e) {
                view = Header.ERROR_PAGE;
//                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                modelMap.addAttribute(Header.MESSAGE, String.format(bundle.getString("userIncorrectData"), e.getMessage()));
//                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("userIncorrectData"), e.getMessage()));
            }
        } else {
            view = Header.REGISTRATION_PAGE;
//            url = Header.PAGE_ROOT + Header.REGISTRATION_PAGE;
//  TODO why session ?
            request.getSession().setAttribute("errors", checkedUser.getErrors());
        }
//        dispatcher = request.getRequestDispatcher(url);
//        try {
//            dispatcher.forward(request, response);
//        } catch (ServletException e) {
//            response.getWriter().write(e.getMessage());
//        }
        return view;
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    @RequestMapping(method = RequestMethod.GET)
    public String goRegistration(HttpSession session) {
//        HttpSession session = request.getSession();
        String view;
//        String url;
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName
            view = Header.LOGIN_WELCOME_PAGE;
//            url = Header.PAGE_ROOT + Header.LOGIN_WELCOME_PAGE;
        } else {
            view = Header.REGISTRATION_PAGE;
//            url = Header.PAGE_ROOT + Header.REGISTRATION_PAGE;
        }
/*
        dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.getWriter().write(e.getMessage());
        }
*/
        return view;
    }

}
