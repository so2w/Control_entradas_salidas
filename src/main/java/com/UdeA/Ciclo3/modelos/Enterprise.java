package com.UdeA.Ciclo3.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="enterprise")

public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String name;
    @Column(unique=true)
    private String document;
    private String phone;
    private String address;

    @ManyToOne
    @JoinColumn (name = "users_id")
     Employee users;
    @ManyToOne
    @JoinColumn(name = "transactions_id")
     Transaction transactions;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate createdAt, updateAt;

    public Enterprise() {
    }

    public Enterprise(String name, String document, String phone, String address, Employee users, Transaction transactions, LocalDate createdAt, LocalDate updateAt) {
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.users = users;
        this.transactions = transactions;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getUsers() {
        return users;
    }

    public void setUsers(Employee users) {
        this.users = users;
    }

    public Transaction getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transactions) {
        this.transactions = transactions;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }
}
