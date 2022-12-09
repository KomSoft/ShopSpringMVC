package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.model.UserRegisteringData;

public interface UserDAO {

    String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException;
    UserRegisteringData getUserByLogin(String login) throws DataBaseException;
    UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException, ValidationException;

}
