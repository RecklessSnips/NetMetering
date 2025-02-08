package com.example.netmetering.controller;

import com.example.netmetering.dto.TransferDTO;
import com.example.netmetering.entities.User;
import com.example.netmetering.exception.AccountException;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private UserService userService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> initTransaction(@RequestBody TransferDTO details){
        System.out.println(details);
        User fromUser = userService.findUserByEmail(details.getFromEmail());
        User toUser = userService.findUserByEmail(details.getToEmail());
        userService.transfer(fromUser, toUser, details.getAmount());
        return ResponseEntity.ok(true);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<String> handleAccountException(AccountException ex) {
        System.out.println("Handling AccountException: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
