package com.komsoft.shopspringmvc.service;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.RegisteredUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final DAOFactory daoFactory;

    public UserService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getFullName(String login, String password) throws DataBaseException {
        String fullName = null;
        RegisteredUser user = daoFactory.getUserDAO().getUserByLogin(login);
        if (user != null && user.isPasswordCorrect(password)) {
            fullName = user.getFullName();
        }
        return fullName;
    }

    public RegisteredUser saveUser(RegisteredUser user) throws DataBaseException, ValidationException {
        return daoFactory.getUserDAO().saveUser(user);
    }
}
