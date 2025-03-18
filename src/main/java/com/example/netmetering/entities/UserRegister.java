package com.example.netmetering.entities;

public class UserRegister {

    private String given_name;
    private String family_name;
    private String password;
    private String email;
    private String location;

    public UserRegister(String given_name, String family_name, String password, String email, String location) {
        this.given_name = given_name;
        this.family_name = family_name;
        this.password = password;
        this.email = email;
        this.location = location;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
