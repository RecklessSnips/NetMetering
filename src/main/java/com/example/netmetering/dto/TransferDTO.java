package com.example.netmetering.dto;

import java.math.BigDecimal;

public class TransferDTO {

    private BigDecimal amount;

    private String fromEmail;

    private String toEmail;

    public TransferDTO(BigDecimal amount, String fromEmail, String toEmail) {
        this.amount = amount;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "amount=" + amount +
                ", fromEmail='" + fromEmail + '\'' +
                ", toEmail='" + toEmail + '\'' +
                '}';
    }
}
