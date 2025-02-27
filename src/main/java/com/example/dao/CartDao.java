package com.example.dao;

import com.example.entity.Cart;

import java.util.List;

public interface CartDao {
    public boolean save(Cart cart);
    public List<Cart> findAll();
}
