package com.example.service;

import com.example.core.Helper;
import com.example.dao.impl.UserDaoImpl;
import com.example.entity.User;

public class UserController {
    private final UserDaoImpl userDao = new UserDaoImpl();

    public User findByLogin(String mail, String password){
        if(!Helper.isMailValid(mail)) return null;
        return userDao.findUser(mail,password);
    }
}
