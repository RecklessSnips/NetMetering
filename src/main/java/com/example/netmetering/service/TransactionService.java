package com.example.netmetering.service;

import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.Transaction;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.TransactionRepository;
import com.example.netmetering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void initiateTransaction(Transaction transaction){
        EnergyAccount account1 = transaction.getFromAccount();
        EnergyAccount account2 = transaction.getToAccount();
        BigDecimal amount = transaction.getAmount();

        account1.withdrawEnergy(amount);
        account2.depositEnergy(amount);

        transaction.setDateTime(new Date(System.currentTimeMillis()));

        recordTransaction(transaction);
    }

    public void completeTransaction(){

    }

    public void recordTransaction(Transaction transaction){
        userRepository.save(transaction.getFromAccount().getUser());
        userRepository.save(transaction.getToAccount().getUser());
        transactionRepository.save(transaction);
    }
}
