package com.example.entity;

import jakarta.persistence.*;

@Entity
@Embeddable
public class Customer {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private TYPE type;
    @Column
    private String phone;
    @Column
    private String mail;
    @Column
    private String address;


    public enum TYPE{
        COMPANY,
        PERSON
    }

    public Customer() {
    }

    public Customer(int id, String address, String phone, String name, String mail, TYPE type) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.mail = mail;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", type=" + type +
                '}';
    }
}
