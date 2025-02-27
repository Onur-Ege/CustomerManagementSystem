package com.example.dao;

import com.example.entity.Basket;

import java.util.List;

public interface BasketDao {

    public boolean save(Basket basket);
    public List<Basket> findAll();
    public boolean delete();

}
