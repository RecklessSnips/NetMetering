package com.example.netmetering.service;

import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.Transaction;
import com.example.netmetering.entities.User;
import com.example.netmetering.exception.AccountException;
import com.example.netmetering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    public User createUser(){
        User user = new User();
        user.setGiven_name("Ahsoka");
        user.setLocation("Coruscant");

        // Default Account
        EnergyAccount account = new EnergyAccount();
        account.setEnergyBalance(new BigDecimal("0"));
        account.setUser(user);
        user.setAccount(account);

        // 验证数据是否被保存
        return userRepository.save(user);
    }

    public void createUser(User user){
        // Default Account
        EnergyAccount account = new EnergyAccount(user, user.getEmail());
        BigDecimal amount = new BigDecimal("0");
        account.setEnergyBalance(amount);
        account.setAvailableBalance(account.getEnergyBalance());
        account.setConsumedBalance(amount);
        account.setCumulativeIncome(amount);
        account.setAverageIncome(amount);
        account.setTransferedBalance(amount);
        account.setUser(user);

        account.setUser(user);
        user.setAccount(account);

        // 验证数据是否被保存
        userRepository.save(user);
    }

    public User findUserByEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.orElse(null);
//        return byEmail.orElseThrow(() -> new AccountException("User doesn't exist"));
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public boolean transfer(User user1, User user2, BigDecimal balance){
        /*
            1. Account check current balance
            2. if enough:
                a. withdraw energy
                b. transfer to the other account
               else:
                throw AccountException
            3. Adjust balance for both accounts
            4. Record this transaction
         */
        BigDecimal energyBalance = user1.getAccount().getAvaliableBalance();
        if (energyBalance.compareTo(balance) < 0){
            System.out.println("AHHO");
            throw new AccountException("Not enough energy balance!");
        }
        Transaction transaction = new Transaction(balance, user1.getAccount(), user2.getAccount());
        transactionService.initiateTransaction(transaction);
        return true;
    }

    public List<User> getGlobalAccounts(){
        return List.of(userRepository.findByEmail("foodBank@gmail.com").get(),
                userRepository.findByEmail("loblaws@gmail.com").get());
    }
}
