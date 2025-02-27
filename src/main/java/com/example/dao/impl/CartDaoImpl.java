package com.example.dao.impl;

import com.example.core.SessionFactoryProvider;
import com.example.dao.CartDao;
import com.example.entity.Cart;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CartDaoImpl implements CartDao {

    ProductDaoImpl productDao = new ProductDaoImpl();
    CustomerDaoImpl customerDao = new CustomerDaoImpl();

    @Override
    public boolean save(Cart cart) {
        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(cart);
            transaction.commit();
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> carts;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            carts = session.createQuery("FROM Cart",Cart.class).list();
            for(Cart cart: carts){
                cart.setProduct(productDao.getById(cart.getProduct_id()));
                cart.setCustomer(customerDao.getById(cart.getCustomer_id()));
            }
        }
        return carts;
    }

}
