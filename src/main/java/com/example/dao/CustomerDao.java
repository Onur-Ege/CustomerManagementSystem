package com.example.dao;

import com.example.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CustomerDao {

    public List<Customer> findAll();
    public boolean save(Customer customer);
    public Customer getById(int id);
    public boolean update(Customer customer);
    public boolean delete(int id);
    public List<Customer> query(String query,String name, Customer.TYPE type);
}
