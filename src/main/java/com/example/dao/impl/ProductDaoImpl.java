package com.example.dao.impl;

import com.example.core.SessionFactoryProvider;
import com.example.dao.ProductDao;
import com.example.entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findAll() {
        List<Product> products;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            products = session.createQuery("FROM Product",Product.class).list();
        }
        return products;
    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Product getById(int id) {
        Product product = null;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            product = session.get(Product.class,id);
        }
        return product;
    }

    @Override
    public boolean update(Product product) {

        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {

        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.remove(product);  // Remove customer if found
                transaction.commit();
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Product> query(String queryStr, String name, String code) {

        List<Product> products = null;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            Query<Product> query = session.createQuery(queryStr,Product.class);
            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }

            if (code != null) {
                query.setParameter("code", "%"+code+"%");
            }
            products = query.list();

        }catch (Exception e){
            e.printStackTrace();
        }
        return products ;
    }
}
