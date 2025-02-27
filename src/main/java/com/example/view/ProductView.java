package com.example.view;

import com.example.core.Helper;
import com.example.entity.Customer;
import com.example.entity.Product;
import com.example.service.ProductController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductView extends JFrame {

    private JPanel container;
    private JTextField fld_product_name;
    private JTextField fld_product_code;
    private JTextField fld_product_price;
    private JTextField fld_product_stock;
    private JButton btn_product_save;
    private JLabel lbl_title;
    private JLabel lbl_product_name;
    private JLabel lbl_product_code;
    private JLabel lbl_product_price;
    private JLabel lbl_product_stock;
    private Product product;
    private ProductController productController;

    public ProductView(Product product){
        this.product = product;
        this.productController = new ProductController();
        this.add(container);

        this.setSize(300, 350);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        if(this.product.getId() == 0){
            this.lbl_title.setText("Add new product");
        }else{
            this.lbl_title.setText("Update product");
            this.fld_product_name.setText(this.product.getName());
            this.fld_product_code.setText(this.product.getCode());
            this.fld_product_price.setText(String.valueOf(this.product.getPrice()));
            this.fld_product_stock.setText(String.valueOf(this.product.getStock()));
        }
        this.btn_product_save.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_product_name) || Helper.isFieldEmpty(fld_product_code) || Helper.isFieldEmpty(fld_product_price) || Helper.isFieldEmpty(fld_product_stock)){
                Helper.showMsg("fill");
            }else {
                this.product.setName(fld_product_name.getText());
                this.product.setCode(fld_product_name.getText());
                this.product.setPrice(Integer.parseInt(fld_product_price.getText()));
                this.product.setStock(Integer.parseInt(fld_product_stock.getText()));

                boolean result = false;
                // if id=0, this means we didn't select a customer that already in database, so execute save
                if (this.product.getId() == 0){
                    result = this.productController.save(this.product);
                }else{
                    result = this.productController.update(this.product);
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
