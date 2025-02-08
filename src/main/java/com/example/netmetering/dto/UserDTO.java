package com.example.netmetering.dto;

import com.example.netmetering.entities.User;

public class UserDTO {

    private String given_name;
    private String family_name;
    private String full_name;
    private String email;
    private EnergyAccountDTO account;

    public UserDTO(User user) {
        this.given_name = user.getGiven_name();
        this.family_name = user.getFamily_name();
        this.full_name = user.getFull_name();
        this.email = user.getEmail();
        this.account = new EnergyAccountDTO(user.getAccount());
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public EnergyAccountDTO getAccount() {
        return account;
    }
}
