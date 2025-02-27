package com.example.view;

import com.example.core.Helper;
import com.example.entity.Basket;
import com.example.entity.Cart;
import com.example.entity.Customer;
import com.example.entity.Product;
import com.example.service.BasketController;
import com.example.service.CartController;
import com.example.service.ProductController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CartView extends JFrame{
    private JPanel container;
    private JTextField fld_order_date;
    private JTextArea txt_cart;
    private JButton btn_new_cart;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JLabel lbl_cart_customer;
    private JLabel lbl_cart_date;
    private JLabel lbl_cart_note;
    private Customer customer;
    private BasketController basketController;
    private CartController cartController;
    private ProductController productController;

    public CartView(Customer customer){
        this.customer = customer;
        this.cartController = new CartController();
        this.basketController = new BasketController();
        this.productController = new ProductController();
        this.add(container);

        this.setSize(300, 350);
        this.setTitle("Create Order");
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        if(customer.getId() == 0){
            Helper.showMsg("Please select a customer!");
            dispose();
        }

        List<Basket> baskets = this.basketController.findALL();
        if(baskets.isEmpty()){
            Helper.showMsg("Please add some products to basket!");
            dispose();
        }

        this.lbl_cart_customer.setText("Customer : " + this.customer.getName());

        btn_new_cart.addActionListener(e -> {
            if(Helper.isFieldEmpty(this.fld_order_date)){
                Helper.showMsg("fill");
            }else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for(Basket basket: baskets){
                    if(basket.getProduct().getStock() == 0) continue;

                    Cart cart = new Cart();
                    cart.setCustomer_id(customer.getId());
                    cart.setProduct_id(basket.getProduct().getId());
                    cart.setPrice(basket.getProduct().getPrice());
                    cart.setDate(LocalDate.parse(this.fld_order_date.getText(),formatter));
                    cart.setNote(this.txt_cart.getText());
                    this.cartController.save(cart);

                    Product unStockProduct = basket.getProduct();
                    unStockProduct.setStock(unStockProduct.getStock()-1);
                    this.productController.update(unStockProduct);

                }
                this.basketController.clear();
                Helper.showMsg("done");
                dispose();
            }
        });
    }

    private void createUIComponents() throws ParseException {
        this.fld_order_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fld_order_date.setText(formatter.format(LocalDate.now()));
    }

}