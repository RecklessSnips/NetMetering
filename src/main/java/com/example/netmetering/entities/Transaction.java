package com.example.netmetering.entities;

import com.example.netmetering.util.Energy;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    private String transactionID;

    @ManyToOne
    @JoinColumn(name = "fromAccountID", referencedColumnName = "accountID")
//    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EnergyAccount fromAccount;

    @ManyToOne
    @JoinColumn(name = "toAccountID", referencedColumnName = "accountID")
//    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EnergyAccount toAccount;
    private BigDecimal amount;
    private Date dateTime;

    public Transaction() {
        long l = System.currentTimeMillis();
        long id = l + 1L;
        this.transactionID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + id;

    }

    public Transaction(BigDecimal amount, EnergyAccount from, EnergyAccount to){
        long l = System.currentTimeMillis();
        long id = l + 1L;
        this.transactionID = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 14) + id;
        this.amount = amount;
        this.fromAccount = from;
        this.toAccount = to;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public EnergyAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(EnergyAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public EnergyAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(EnergyAccount toAccount) {
        this.toAccount = toAccount;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", fromAccountID=" + fromAccount.getAccountID() +
                ", toAccountID=" + toAccount.getAccountID() +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }
}
