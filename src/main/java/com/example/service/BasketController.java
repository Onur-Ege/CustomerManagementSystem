package com.example.service;

import com.example.dao.impl.BasketDaoImpl;
import com.example.entity.Basket;

import java.util.List;

public class BasketController {

    private final BasketDaoImpl basketDao = new BasketDaoImpl();
    public boolean save(Basket basket){return this.basketDao.save(basket);}
    public List<Basket> findALL(){return this.basketDao.findAll();}
    public boolean clear(){return this.basketDao.delete();}
}
