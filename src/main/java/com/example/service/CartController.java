package com.example.service;

import com.example.dao.impl.CartDaoImpl;
import com.example.entity.Cart;

import java.util.List;

public class CartController {

    private final CartDaoImpl cartDao = new CartDaoImpl();
    public boolean save(Cart cart) { return cartDao.save(cart); }
    public List<Cart> findAll() { return cartDao.findAll(); }

}
