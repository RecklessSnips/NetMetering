package com.example.netmetering.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {

    private String fromAccountId;
    private String toAccountId;
    private UserDTO userDTO;
    private BigDecimal amount;
    private Date dateTime;

    public TransactionDTO(){}

    public TransactionDTO(String fromAccountId, String toAccountId, UserDTO userDTO, BigDecimal amount, Date dateTime) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.userDTO = userDTO;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
