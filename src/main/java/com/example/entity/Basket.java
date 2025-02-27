package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Basket {
    @Id
    private int id;
    @Column
    private int product_id;
    @Transient
    private Product product;

    public Basket() {
    }

    public Basket(int id, int product_id, Product product) {
        this.id = id;
        this.product_id = product_id;
        this.product = product;
    }

    public Basket(int product_id) {
        this.product_id = product_id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", product=" + product +
                '}';
    }
}
