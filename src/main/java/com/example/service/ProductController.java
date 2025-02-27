package com.example.service;

import com.example.core.Helper;
import com.example.core.Item;
import com.example.dao.impl.ProductDaoImpl;
import com.example.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private final ProductDaoImpl productDao = new ProductDaoImpl();

    public List<Product> findALL(){
        return this.productDao.findAll();
    }

    public boolean save(Product product){return this.productDao.save(product);}

    public Product getById(int id){return this.productDao.getById(id);}

    public boolean update(Product product){
        if(this.getById(product.getId()) == null){
            Helper.showMsg("Product with id " + product.getId() + " could not be found.");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg("Product with id " + id + " could not be found.");
            return false;
        }
        return this.productDao.delete(id);
    }

    public List<Product> filter(String name, String code, Item isStock){
        String query = "FROM Product";
        ArrayList<String> whereList = new ArrayList<>();

        if (name != null && !name.isEmpty()){
            whereList.add("name LIKE :name");
        }
        if(code != null){
            whereList.add("code LIKE :code");
        }
        if(isStock != null){
            if(isStock.getKey() == 1){
                whereList.add("stock > 0");
            }else {
                whereList.add("stock <= 0");            }
        }
        if(!whereList.isEmpty()){
            String whereQuery = String.join(" AND ",whereList);
            query += " WHERE " + whereQuery;
        }
        return this.productDao.query(query,name,code);
    }
}
