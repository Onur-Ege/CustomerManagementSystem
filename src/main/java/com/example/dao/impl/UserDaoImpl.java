package com.example.dao.impl;

import com.example.core.SessionFactoryProvider;
import com.example.dao.UserDao;
import com.example.entity.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findAll() {
        List<User> users;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()){
            users = session.createQuery("from User",User.class).list();
        }
        return users;
    }

    @Override
    public User findUser (String mail, String password){
        User user = null;
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {

            Query<User> query = session.createQuery("FROM User WHERE mail=:mail AND password=:password", User.class);
            query.setParameter("mail", mail);
            query.setParameter("password", password);

            try {
                user = query.getSingleResult();
            } catch (NoResultException e) {
                // No user found - user remains null
            }
        }
        return user;
    }

}
