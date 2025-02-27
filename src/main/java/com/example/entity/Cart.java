package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
public class Cart {

    @Id
    private int id;
    @Column
    private int customer_id;
    @Column
    private int product_id;
    @Column
    private int price;
    @Column
    private String note;
    @Column
    private LocalDate date;
    @Transient
    private Customer customer;
    @Transient
    private Product product;

    public Cart() {
    }

    public Cart(int customer_id, int id, int price, int product_id, LocalDate date, String note, Customer customer, Product product) {
        this.customer_id = customer_id;
        this.id = id;
        this.price = price;
        this.product_id = product_id;
        this.date = date;
        this.note = note;
        this.customer = customer;
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", product_id=" + product_id +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", customer=" + customer +
                ", product=" + product +
                '}';
    }
}
