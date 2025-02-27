package com.example.dao.impl;

import com.example.core.SessionFactoryProvider;
import com.example.dao.CustomerDao;
import com.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public List<Customer> findAll() {
        List<Customer> customers;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            customers = session.createQuery("FROM Customer",Customer.class).list();
        }
        return customers;
    }

    @Override
    public boolean save(Customer customer) {
        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Customer getById(int id) {
        Customer customer = null;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            customer = session.get(Customer.class,id);
        }
        return customer;
    }

    @Override
    public boolean update(Customer customer) {

        Transaction transaction = null;
        boolean result = false;

        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(customer);
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
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.remove(customer);  // Remove customer if found
                transaction.commit();
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Customer> query(String queryStr, String name, Customer.TYPE type) {

        List<Customer> customers = null;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            Query<Customer> query = session.createQuery(queryStr,Customer.class);
            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }

            if (type != null) {
                query.setParameter("type", type);
            }
            customers = query.list();

        }catch (Exception e){
            e.printStackTrace();
        }
        return customers ;
    }
}
