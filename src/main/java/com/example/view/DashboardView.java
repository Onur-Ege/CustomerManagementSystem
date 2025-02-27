package com.example.view;

import com.example.core.Helper;
import com.example.core.Item;
import com.example.entity.*;
import com.example.service.BasketController;
import com.example.service.CartController;
import com.example.service.CustomerController;
import com.example.service.ProductController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DashboardView extends JFrame{
    private JPanel container;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JLabel lbl_welcome;
    private JTable tbl_customer;
    private JScrollPane scrl_customer;
    private JPanel pnl_customer;
    private JTextField fld_f_customer_name;
    private JComboBox<Customer.TYPE> cmb_f_customer_type;
    private JButton btn_f_customer_search;
    private JButton btn_f_customer_reset;
    private JButton btn_f_customer_new;
    private JPanel pnl_customer_filter;
    private JLabel lbl_f_customer_name;
    private JLabel lbl_f_customer_type;
    private JPanel pnl_product;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_f_product_search;
    private JButton btn_f_product_reset;
    private JButton btn_f_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private JPanel pnl_basket;
    private JPanel pnl_basket_top;
    private JScrollPane scrl_basket;
    private JTextField fld_basket_price;
    private JTextField fld_basket_count;
    private JButton btn_basket_reset;
    private JButton btn_basket_new;
    private JComboBox<Item> cmb_basket_customer;
    private JLabel lbl_basket_customer;
    private JLabel lbl_basket_price;
    private JLabel lbl_basket_count;
    private JTable tbl_basket;
    private JPanel pnl_orders;
    private JScrollPane scrl_orders;
    private JTable tbl_orders;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private BasketController basketController;
    private CartController cartController;
    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private DefaultTableModel tmdl_basket = new DefaultTableModel();
    private DefaultTableModel tmdl_orders = new DefaultTableModel();
    private JPopupMenu poppup_customer = new JPopupMenu();
    private JPopupMenu poppup_product = new JPopupMenu();

    public DashboardView(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();
        this.cartController = new CartController();
        if (user == null) {
            Helper.showMsg("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(1050, 515);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
        this.lbl_welcome.setText("Welcome " + user.getName());

        this.btn_logout.addActionListener(e -> {

            dispose();
            LoginView loginView = new LoginView();
        });

        //Customer Tab
        loadCustomerTable(null);
        loadCustomerPoppupMenu();
        loadCustomerButtonEvent();

        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);

        //Product Tab
        loadProductTable(null);
        loadProductPoppupMenu();
        loadProductButtonEvent();

        this.cmb_f_product_stock.addItem(new Item(1,"In stock"));
        this.cmb_f_product_stock.addItem(new Item(2,"Out of stock"));
        this.cmb_f_product_stock.setSelectedItem(null);

        //Basket Tab
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();

        //Cart Tab
        loadCartTable();

    }

    private void loadCartTable(){
        Object[] columnName = {"Id","Product","Customer","Price","Date","Note"};
        this.tmdl_orders.setColumnIdentifiers(columnName);

        List<Cart> carts = this.cartController.findAll();

        // clear the table
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_orders.getModel();
        clearModel.setRowCount(0);

        for(Cart cart : carts){
            Object[] rowOfTable = {
                    cart.getId(),
                    cart.getCustomer().getName(),
                    cart.getProduct().getName(),
                    cart.getPrice(),
                    cart.getDate(),
                    cart.getNote()
            };
            this.tmdl_orders.addRow(rowOfTable);
        }

        this.tbl_orders.setModel(tmdl_orders);
        this.tbl_orders.getTableHeader().setReorderingAllowed(false);
        this.tbl_orders.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_orders.setEnabled(true);
    }

    private void loadBasketCustomerCombo(){
        List<Customer> customers = this.customerController.findALL();
        this.cmb_basket_customer.removeAllItems();
        for(Customer customer: customers){
            int comboKey = customer.getId();
            String comboValue = customer.getName();
            this.cmb_basket_customer.addItem(new Item(comboKey,comboValue));
        }
        this.cmb_basket_customer.setSelectedItem(null);
    }

    private void loadBasketButtonEvent(){
        this.btn_basket_reset.addActionListener(e -> {
            if(this.basketController.clear()){
                Helper.showMsg("done");
                loadBasketTable();
            }else {
                Helper.showMsg("error");
            }

        });

        this.btn_basket_new.addActionListener(e -> {
            Item selectedCustomer = (Item) this.cmb_basket_customer.getSelectedItem();
            if(selectedCustomer == null){
                Helper.showMsg("Please select a customer!");
            }else{
                Customer customer = this.customerController.getById(selectedCustomer.getKey());
                List<Basket> baskets = this.basketController.findALL();
                if(customer.getId() == 0){
                    Helper.showMsg("Customer couldnot be found");
                }else if(baskets.isEmpty()){
                    Helper.showMsg("Please add some products to basket");
                }else {
                    CartView cartView = new CartView(customer);
                    cartView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            loadBasketTable();
                            loadProductTable(null);
                            loadCartTable();
                        }
                    });
                }
            }
        });
    }

    private void loadBasketTable(){
        Object[] columnName = {"Id","Product Name","Code","Price","Stock"};
        this.tmdl_basket.setColumnIdentifiers(columnName);

        List<Basket> baskets = this.basketController.findALL();

        // clear the table
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_basket.getModel();
        clearModel.setRowCount(0);

        int totalPrice = 0;
        for(Basket basket : baskets){
            Object[] rowOfTable = {
                    basket.getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getCode(),
                    basket.getProduct().getPrice(),
                    basket.getProduct().getStock()
            };
            this.tmdl_basket.addRow(rowOfTable);
            totalPrice += basket.getProduct().getPrice();
        }

        this.fld_basket_price.setText(totalPrice + " $");
        this.fld_basket_count.setText(baskets.size() + " piece");

        this.tbl_basket.setModel(tmdl_basket);
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_basket.setEnabled(true);
    }

    private void loadCustomerButtonEvent(){

        this.btn_f_customer_new.addActionListener(e -> {
            CustomerView customerView = new CustomerView(new Customer());
            customerView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });
        });

        this.btn_f_customer_search.addActionListener(e -> {
            List<Customer> filteredCustomers = this.customerController.filter(
                    this.fld_f_customer_name.getText(),
                    (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem()
            );
            loadCustomerTable(filteredCustomers);
        });

        this.btn_f_customer_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_f_customer_name.setText("");
            this.cmb_f_customer_type.setSelectedItem(null);
        });
    };

    private void loadCustomerPoppupMenu(){
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.poppup_customer.add("Update").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString());
            CustomerView customerView = new CustomerView(this.customerController.getById(selectedId));
            customerView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });
        });

        this.poppup_customer.add("Delete").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(),0).toString());
            if(Helper.confirm("sure")){
                if(this.customerController.delete(selectedId)){
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_customer.setComponentPopupMenu(this.poppup_customer);
    }

    private void loadCustomerTable(List<Customer> customers){
        Object[] columnName = {"Id","Customer Name","Type","Phone","E-mail","Address"};
        this.tmdl_customer.setColumnIdentifiers(columnName);

        if(customers == null){
            customers = this.customerController.findALL();
        }
        // clear the table
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        for(Customer customer : customers){
            Object[] rowOfTable = {
                    customer.getId(),
                    customer.getName(),
                    customer.getType(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()
            };
            this.tmdl_customer.addRow(rowOfTable);
        }
        this.tbl_customer.setModel(tmdl_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.setEnabled(true);
    }

    private void loadProductButtonEvent(){

        this.btn_f_product_new.addActionListener(e -> {
            ProductView productView = new ProductView(new Product());
            productView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });

        this.btn_f_product_search.addActionListener(e -> {
            List<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
        });

        this.btn_f_product_reset.addActionListener(e -> {
            loadProductTable(null);
            this.fld_f_product_name.setText("");
            this.fld_f_product_code.setText("");
            this.cmb_f_product_stock.setSelectedItem(null);
        });
    };

    private void loadProductPoppupMenu(){
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.poppup_product.add("Add to basket").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(),0).toString());
            Product basketProduct = this.productController.getById(selectedId);
            if(basketProduct.getStock() <= 0){
                Helper.showMsg("This product is out of stock");
            }else {
                Basket basket = new Basket(basketProduct.getId());
                if (this.basketController.save(basket)){
                    Helper.showMsg("done");
                    loadBasketTable();
                }else {
                    Helper.showMsg("error");
                }
            }
        });

        this.poppup_product.add("Update").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(),0).toString());
            ProductView productView = new ProductView(this.productController.getById(selectedId));
            productView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });

        });

        this.poppup_product.add("Delete").addActionListener(e -> {
            int selectedId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(),0).toString());
            if(Helper.confirm("sure")){
                if(this.productController.delete(selectedId)){
                    Helper.showMsg("done");
                    loadProductTable(null);
                    loadBasketTable();
                }else {
                    Helper.showMsg("error");
                }
            }

        });

        this.tbl_product.setComponentPopupMenu(this.poppup_product);
    }

    private void loadProductTable(List<Product> products){
        Object[] columnName = {"Id","Product Name","Code","Price","Stock"};
        this.tmdl_product.setColumnIdentifiers(columnName);

        if(products == null){
            products = this.productController.findALL();
        }
        // clear the table
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        for(Product product : products){
            Object[] rowOfTable = {
                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getStock()
            };
            this.tmdl_product.addRow(rowOfTable);
        }
        this.tbl_product.setModel(tmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(true);
    }


}
