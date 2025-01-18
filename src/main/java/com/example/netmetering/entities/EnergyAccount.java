package com.example.netmetering.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "energy_account")
public class EnergyAccount {

    @Id
    private String accountID;
    @OneToOne(mappedBy = "account")
    private User user;
    private String email;
    private BigDecimal energyBalance;

    public EnergyAccount(){
        long l = System.currentTimeMillis();
        long id = l + 1L;
        this.accountID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + id;
    }

    public EnergyAccount(User user, String email){
        long l = System.currentTimeMillis();
        long id = l + 1L;
        this.accountID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + id;
        this.user = user;
        this.email = email;
    }

    public void depositEnergy(BigDecimal amount){
        this.energyBalance = this.energyBalance.add(amount);
    }

    public void withdrawEnergy(BigDecimal amount){
        this.energyBalance = this.energyBalance.subtract(amount);
    }

    public String getAccountID() {
        return accountID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getEnergyBalance() {
        return energyBalance;
    }

    public void setEnergyBalance(BigDecimal energyBalance) {
        this.energyBalance = energyBalance;
    }

    @Override
    public String toString() {
        return "EnergyAccount{" +
                "accountID=" + accountID +
                ", energyBalance=" + energyBalance +
                '}';
    }
}
