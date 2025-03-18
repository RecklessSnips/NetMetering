package com.example.netmetering.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String userID;
    private String given_name;
    private String family_name;
    private String full_name;
    private String email;
    private String password;
    private String location;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountID")
    private EnergyAccount account;

    public User(){
        this.userID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + System.currentTimeMillis();
    };

    public User(String given_name, String family_name, String email, String password, String location){
        this.userID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + System.currentTimeMillis();
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.family_name = family_name;
        this.full_name = given_name + " " + family_name;
        this.location = location;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EnergyAccount getAccount() {
        return account;
    }

    public void setAccount(EnergyAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + full_name + '\'' +
                ", location='" + location + '\'' +
                ", account=" + account +
                '}';
    }
}
