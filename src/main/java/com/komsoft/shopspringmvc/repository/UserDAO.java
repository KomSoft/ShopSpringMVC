package com.komsoft.shopspringmvc.repository;

import com.komsoft.shopspringmvc.exception.DataBaseException;
import com.komsoft.shopspringmvc.exception.ValidationException;
import com.komsoft.shopspringmvc.model.RegisteredUser;

public interface UserDAO {

    String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException;
    RegisteredUser getUserByLogin(String login) throws DataBaseException;
    RegisteredUser saveUser(RegisteredUser user) throws DataBaseException, ValidationException;

}
