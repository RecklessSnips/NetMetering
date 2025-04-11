package com.example.netmetering.controller;

import com.example.netmetering.dto.TransactionDTO;
import com.example.netmetering.dto.UserDTO;
import com.example.netmetering.entities.Transaction;
import com.example.netmetering.entities.User;
import com.example.netmetering.exception.AccountException;
import com.example.netmetering.service.TransactionService;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/info")
    public ResponseEntity<?> getCurrentUser(){
        User currentUser = userService.getCurrentUser();
        return currentUser != null
                ? ResponseEntity.ok(new UserDTO(currentUser))
                : ResponseEntity.badRequest().body("Cannot find any current logged in user");
    }

    /*
    Get all the fake accounts such as food bank, Loblaws
     */
    @GetMapping("/globalAccounts")
    public List<UserDTO> getGlobalAccounts() {
        List<UserDTO> list = new ArrayList<>();
        for(User user: userService.getGlobalAccounts()){
            list.add(new UserDTO(user));
        }
        return list;
    }

    @PostMapping("/transaction")
    public ResponseEntity<List<TransactionDTO>> getTransactionById(@RequestBody UserDTO userDTO){
        System.out.println("DTO: ");
        System.out.println(userDTO.getEmail());
        User user = userService.findUserByEmail(userDTO.getEmail());
        System.out.println(user.getAccount().getAccountID());
        System.out.println(transactionService.getTransactionByFromID(user.getAccount().getAccountID()).size());
        List<Transaction> transactions = transactionService.getTransactionByFromID(user.getAccount().getAccountID());
        List<TransactionDTO> transactionDTOS = new LinkedList<>();
        for (Transaction transaction: transactions){
            transactionDTOS.add(new TransactionDTO(
                    transaction.getFromAccount().getAccountID(),
                    transaction.getToAccount().getAccountID(),
                    new UserDTO(transaction.getToAccount().getUser()),
                    transaction.getAmount(),
                    transaction.getDateTime()
                    ));
        }
        return ResponseEntity.ok(transactionDTOS);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody BigDecimal amount){
        User currentUser = userService.getCurrentUser();
        userService.deposit(currentUser, amount);
        return ResponseEntity.ok(true);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<String> handleAccountException(AccountException ex) {
        System.out.println("Handling AccountException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
