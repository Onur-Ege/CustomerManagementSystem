package com.example.core;

import com.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {

    private static SessionFactoryProvider sessionFactoryProvider;
    private final SessionFactory sessionFactory;

    private SessionFactoryProvider(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public static SessionFactoryProvider getInstance(){
        if(sessionFactoryProvider == null){
            sessionFactoryProvider = new SessionFactoryProvider();
        }
        return sessionFactoryProvider;
    }

    public SessionFactory getSessionFactory() {return sessionFactory;}

}
