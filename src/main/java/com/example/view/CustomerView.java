package com.example.view;

import com.example.core.Helper;
import com.example.entity.Customer;
import com.example.service.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerView extends JFrame {
    private CustomerController customerController;
    private JPanel container;
    private JTextField fld_customer_name;
    private JTextField fld_customer_phone;
    private JTextField fld_customer_mail;
    private JButton btn_customer_save;
    private JLabel lbl_customer_title;
    private JLabel lbl_customer_name;
    private JLabel lbl_customer_type;
    private JComboBox<Customer.TYPE> cmb_customer_type;
    private JLabel lbl_customer_phone;
    private JLabel lbl_customer_mail;
    private JLabel lbl_customer_address;
    private JTextArea txt_customer_address;
    private Customer customer;

    public CustomerView(Customer customer){
        this.customer = customer;
        this.add(container);
        this.customerController = new CustomerController();

        this.setSize(300, 500);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        this.cmb_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));

        if(this.customer.getId() == 0){
            this.lbl_customer_title.setText("Add new customer");
        }else{
            this.lbl_customer_title.setText("Update customer");
            this.fld_customer_name.setText(this.customer.getName());
            this.fld_customer_phone.setText(this.customer.getPhone());
            this.fld_customer_mail.setText(this.customer.getMail());
            this.txt_customer_address.setText(this.customer.getAddress());
            this.cmb_customer_type.getModel().setSelectedItem(this.customer.getType());
        }

        this.btn_customer_save.addActionListener(e -> {
            if(Helper.isFieldEmpty(this.fld_customer_name) || Helper.isFieldEmpty(this.fld_customer_phone)){
                Helper.showMsg("fill");
            }else if(!Helper.isFieldEmpty(this.fld_customer_mail) && !Helper.isMailValid(this.fld_customer_mail.getText())){
                Helper.showMsg("please enter valid E-mail!");
            }else{

                this.customer.setName(this.fld_customer_name.getText());
                this.customer.setPhone(this.fld_customer_phone.getText());
                this.customer.setMail(this.fld_customer_mail.getText());
                this.customer.setAddress(this.txt_customer_address.getText());
                this.customer.setType((Customer.TYPE) this.cmb_customer_type.getSelectedItem());

                boolean result = false;
                // if id=0, this means we didn't select a customer that already in database, so execute save
                if (this.customer.getId() == 0){
                    result = this.customerController.save(this.customer);
                }else{
                    result = this.customerController.update(this.customer);
                }

                if (result){
                    Helper.showMsg("done");
                    dispose();
                }else {
                    Helper.showMsg("error");
                }


            }
        });
    }
}
