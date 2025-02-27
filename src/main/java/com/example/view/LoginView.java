package com.example.view;

import com.example.core.Helper;
import com.example.entity.User;
import com.example.service.UserController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{
    private JPanel container;
    private JTextField fld_mail;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JPanel pnl_top;
    private JLabel lbl_top;
    private JPanel pnl_bottom;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private UserController userController;

    public LoginView(){
        this.userController = new UserController();

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(300,350);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);
        this.btn_login.addActionListener(e -> {

            if(!Helper.isMailValid(this.fld_mail.getText())){
                Helper.showMsg("Geçerli bir e-posta giriniz!");
            }else if (Helper.isFieldEmpty(this.fld_mail) || Helper.isFieldEmpty(this.fld_password)){
                Helper.showMsg("fill");
            }else{
                User user = this.userController.findByLogin(fld_mail.getText(),fld_password.getText());
                if (user == null){
                    Helper.showMsg("user not found");
                }else {
                    this.dispose();
                    DashboardView dashboardView = new DashboardView(user);
                }
            }
        });
    }
}
