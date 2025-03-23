package com.example.netmetering.controller;

import com.example.netmetering.IAM.user_details.CustomUserDetails;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

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
        // Get current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = null;
        if(principal instanceof DefaultOidcUser){
            // Google user
            System.out.println("Google");
            DefaultOidcUser currentUser = (DefaultOidcUser)authentication.getPrincipal();
            String email = currentUser.getEmail();
            user = userService.findUserByEmail(email);

        }else if(principal instanceof DefaultOAuth2User){
            // Facebook user
            System.out.println("Facebook");
            DefaultOAuth2User currentUser = (DefaultOAuth2User)authentication.getPrincipal();
            String email = currentUser.getAttribute("email");
            user = userService.findUserByEmail(email);

        }else if (principal instanceof CustomUserDetails currentUser){
            user= userService.findUserByEmail(currentUser.getUsername());
        }
        if (user != null){
            return ResponseEntity.ok(new UserDTO(user));
        }else {
            return ResponseEntity.badRequest().body("Cannot find any current logged in user");
        }

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

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<String> handleAccountException(AccountException ex) {
        System.out.println("Handling AccountException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
