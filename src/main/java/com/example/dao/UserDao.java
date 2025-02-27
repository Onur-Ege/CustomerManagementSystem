package com.example.dao;

import com.example.entity.User;

import java.util.List;

public interface UserDao {
    public User findUser(String mail, String password);
    public List<User> findAll();
}
