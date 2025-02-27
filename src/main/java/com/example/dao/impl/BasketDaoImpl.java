package com.example.dao.impl;

import com.example.core.SessionFactoryProvider;
import com.example.dao.BasketDao;
import com.example.entity.Basket;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BasketDaoImpl implements BasketDao {

    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    public boolean save(Basket basket) {
        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(basket);
            transaction.commit();
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Basket> findAll() {
        List<Basket> baskets;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            baskets = session.createQuery("FROM Basket",Basket.class).list();
            for(Basket basket: baskets){
                basket.setProduct(productDao.getById(basket.getProduct_id()));
            }
        }
        return baskets;
    }

    @Override
    public boolean delete() {

        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Basket");
            query.executeUpdate();
            transaction.commit();
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
