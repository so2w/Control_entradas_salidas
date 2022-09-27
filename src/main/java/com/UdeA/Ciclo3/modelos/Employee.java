package com.UdeA.Ciclo3.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String email;
    private String profile;
    private String rol;
    @ManyToOne
    @JoinColumn(name = "enterprises_id")
    Enterprise enterprise;

    @ManyToOne
    @JoinColumn(name = "transactions_id")
    Transaction transactions;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt, updateAt;

    public Employee() {
    }

    public Employee(String email, String profile, String rol, Enterprise enterprise, Transaction transactions, Date createdAt, Date updateAt) {
        this.email = email;
        this.profile = profile;
        this.rol = rol;
        this.enterprise = enterprise;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Transaction getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transactions) {
        this.transactions = transactions;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}