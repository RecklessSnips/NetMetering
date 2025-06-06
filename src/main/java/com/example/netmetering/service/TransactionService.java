package com.example.netmetering.service;

import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.Transaction;
import com.example.netmetering.repository.TransactionRepository;
import com.example.netmetering.repository.UserRepository;
import com.example.netmetering.util.Energy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

        // Transfer energy
        account1.withdrawEnergy(amount);
        account2.depositEnergy(amount);

        // Increase account2's income, and decrease account1's
        Energy energy = new Energy(amount);
        // The dollar amount equivalent to the KWH produced
        BigDecimal income = energy.getDollars();
        // Start
        account2.increaseCumulativeIncome(income);
        account1.decreaseCumulativeIncome(income);

        // Increase account1's transfered balance
        account1.increaseTransferedBalance(amount);

        transaction.setDateTime(new Date(System.currentTimeMillis()));

        recordTransaction(transaction);
    }

    public void recordTransaction(Transaction transaction){
        userRepository.save(transaction.getFromAccount().getUser());
        userRepository.save(transaction.getToAccount().getUser());
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionByFromID(String fromID){
        return transactionRepository.findByFromAccountID(fromID);
    }

    public void getALlTransactions(){
        Iterable<Transaction> all = transactionRepository.findAll();
        all.forEach(System.out::println);
        System.out.println("---------------------");
        Iterator<Transaction> iterator = all.iterator();
        int count = 0;
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            count++;
        }
        System.out.println(count);
    }
}
