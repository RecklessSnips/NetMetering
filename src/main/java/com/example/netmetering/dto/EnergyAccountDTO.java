package com.example.netmetering.dto;

import com.example.netmetering.entities.EnergyAccount;

import java.math.BigDecimal;

public class EnergyAccountDTO {
    private String email;
    private BigDecimal energyBalance;
    private BigDecimal availableBalance;
    private BigDecimal transferedBalance;
    private BigDecimal consumedBalance;
    private BigDecimal cumulativeIncome;
    private BigDecimal averageIncome;

    public EnergyAccountDTO(){}

    public EnergyAccountDTO(EnergyAccount account) {
        this.email = account.getEmail();
        this.energyBalance = account.getEnergyBalance();
        this.availableBalance = account.getAvaliableBalance();
        this.transferedBalance = account.getTransferedBalance();
        this.consumedBalance = account.getConsumedBalance();
        this.cumulativeIncome = account.getCumulativeIncome();
        this.averageIncome = account.getAverageIncome();
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getEnergyBalance() {
        return energyBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getTransferedBalance() {
        return transferedBalance;
    }

    public BigDecimal getConsumedBalance() {
        return consumedBalance;
    }

    public BigDecimal getCumulativeIncome() {
        return cumulativeIncome;
    }

    public BigDecimal getAverageIncome() {
        return averageIncome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnergyBalance(BigDecimal energyBalance) {
        this.energyBalance = energyBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setTransferedBalance(BigDecimal transferedBalance) {
        this.transferedBalance = transferedBalance;
    }

    public void setConsumedBalance(BigDecimal consumedBalance) {
        this.consumedBalance = consumedBalance;
    }

    public void setCumulativeIncome(BigDecimal cumulativeIncome) {
        this.cumulativeIncome = cumulativeIncome;
    }

    public void setAverageIncome(BigDecimal averageIncome) {
        this.averageIncome = averageIncome;
    }
}
