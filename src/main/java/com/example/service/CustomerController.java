package com.example.service;

import com.example.core.Helper;
import com.example.dao.impl.CustomerDaoImpl;
import com.example.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    private final CustomerDaoImpl customerDao = new CustomerDaoImpl();

    public List<Customer> findALL(){
        return this.customerDao.findAll();
    }
    public boolean save(Customer customer){
        return this.customerDao.save(customer);
    }
    public Customer getById(int id){
        return this.customerDao.getById(id);
    }
    public boolean update(Customer customer){
        if(this.getById(customer.getId()) == null){
            Helper.showMsg("User with id " + customer.getId() + " could not be found.");
            return false;
        }
        return this.customerDao.update(customer);
    }
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg("User with id " + id + " could not be found.");
            return false;
        }
        return this.customerDao.delete(id);
    }
    public List<Customer> filter(String name, Customer.TYPE type){
        String query = "FROM Customer";
        ArrayList<String> whereList = new ArrayList<>();

        if (name != null && !name.isEmpty()){
            whereList.add("name LIKE :name");
        }
        if(type != null){
            whereList.add("type = :type");
        }
        if(!whereList.isEmpty()){
            String whereQuery = String.join(" AND ",whereList);
            query += " WHERE " + whereQuery;
        }
        return this.customerDao.query(query,name,type);
    }
}
