package com.komsoft.shopspringmvc.service;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.factory.DAOFactory;
import com.komsoft.shopspringmvc.model.UserRegisteringData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final DAOFactory daoFactory;

    public UserService() {
        this.daoFactory = DAOFactory.getInstance();
    }

    public String getFullName(String login, String password) throws DataBaseException {
        String fullName = null;
//        UserDAO userDAO = daoFactory.getUserDAO();
//                  without using BCrypt can use this way
//                    fullName = userRepository.getFullNameByLoginAndPassword(login, UserRegisteringData.encryptPassword(password));
//                    if (fullName != null) {
//        UserRegisteringDataModel user = null;
        UserRegisteringData user = daoFactory.getUserDAO().getUserByLogin(login);
        if (user != null && user.isPasswordCorrect(password)) {
            fullName = user.getFullName();
        }
        return fullName;
    }
}
