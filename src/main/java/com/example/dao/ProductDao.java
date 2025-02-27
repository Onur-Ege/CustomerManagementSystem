package com.example.dao;

import com.example.entity.Product;

import java.util.List;

public interface ProductDao {

    public List<Product> findAll();
    public boolean save(Product product);
    public Product getById(int id);
    public boolean update(Product product);
    public boolean delete(int id);
    public List<Product> query(String queryStr, String name, String code);

}
