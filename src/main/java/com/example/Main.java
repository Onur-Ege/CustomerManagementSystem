package com.example;


import com.example.core.Helper;
import com.example.entity.User;
import com.example.service.UserController;
import com.example.view.DashboardView;
import com.example.view.LoginView;

public class Main {
    public static void main(String[] args) {
        Helper.setTheme();
        LoginView loginView = new LoginView();
//        UserController userController = new UserController();
//        User user = userController.findByLogin("onur@gmail.com","2121");
//        DashboardView dashboardView = new DashboardView(user);
    }
}