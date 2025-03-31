package com.example.netmetering.controller;

import com.example.netmetering.dto.UserDTO;
import com.example.netmetering.entities.User;
import com.example.netmetering.service.ContactService;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @PostMapping("/addContact")
    public ResponseEntity<?> addContact(@RequestBody List<String> emails) {
        /*
         Change the UserDTO into User
         No need to check for null since
         we are calling this API from the getAllContacts()
        */
        User[] users = emails.stream()
                .map(email -> userService.findUserByEmail(email))
                .toArray(size -> new User[size]);
        boolean ifSuccess = contactService.addContact(users);
        return ResponseEntity.ok(ifSuccess);
    }

    @PostMapping("/deleteContact")
    public ResponseEntity<Boolean> deleteContact(@RequestBody List<String> emails){
        User[] users = emails.stream()
                .map(email -> userService.findUserByEmail(email))
                .toArray(size -> new User[size]);
        boolean ifSuccess = contactService.deleteContact(users);
        return ResponseEntity.ok(ifSuccess);
    }

    @GetMapping("/getAllContacts")
    public ResponseEntity<List<UserDTO>> getAllContacts(){
        List<User> allContacts = contactService.getAllContacts();
        List<UserDTO> contacts = allContacts.stream()
                .map(user -> new UserDTO(user))
                .toList();
        // If it's null, let the frontend handle
        return ResponseEntity.ok(contacts);
    }
}
