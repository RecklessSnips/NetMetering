package com.example.netmetering.entities;

import com.example.netmetering.util.Energy;
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
    // Total energy generated (never decrease)
    private BigDecimal energyBalance;
    // Energy to transfer (same as total energy but can decrease)
    private BigDecimal availableBalance;
    // Energy transfered
    private BigDecimal transferedBalance;
    // Consumed by the customer
    private BigDecimal consumedBalance;
    // calculated through the availableBalance
    private BigDecimal cumulativeIncome;
    // Not decided yet
    private BigDecimal averageIncome;

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
        this.availableBalance = this.availableBalance.add(amount);
    }

    public void withdrawEnergy(BigDecimal amount){
        this.availableBalance = this.availableBalance.subtract(amount);
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

    public BigDecimal getAvaliableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getTransferedBalance() {
        return transferedBalance;
    }

    public void setTransferedBalance(BigDecimal transferedBalance) {
        this.transferedBalance = transferedBalance;
    }

    public void setCumulativeIncome(BigDecimal cumulativeIncome) {
        this.cumulativeIncome = cumulativeIncome;
    }

    public void setAverageIncome(BigDecimal averageIncome) {
        this.averageIncome = averageIncome;
    }

    public BigDecimal getConsumedBalance() {
        return consumedBalance;
    }

    public void setConsumedBalance(BigDecimal consumedBalance) {
        this.consumedBalance = consumedBalance;
    }

    public BigDecimal getCumulativeIncome() {
        return cumulativeIncome;
    }

    // Increase both total energy and available energy
    public void increaseEnergyBalance(BigDecimal amount){
        this.energyBalance = this.energyBalance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
        // When deposit, the income will increase accordingly
        increaseCumulativeIncome(new Energy(availableBalance).getDollars());
    }

    public void increaseTransferedBalance(BigDecimal balance){
        this.transferedBalance = this.transferedBalance.add(balance);
    }

    public void increaseCumulativeIncome(BigDecimal dollar){
        this.cumulativeIncome = this.cumulativeIncome.add(dollar);
    }

    public void decreaseCumulativeIncome(BigDecimal dollar){
        this.cumulativeIncome = this.cumulativeIncome.subtract(dollar);
    }

    public BigDecimal getAverageIncome() {
        return averageIncome;
    }

    @Override
    public String toString() {
        return "EnergyAccount{" +
                "accountID=" + accountID +
                ", energyBalance=" + energyBalance +
                '}';
    }
}
