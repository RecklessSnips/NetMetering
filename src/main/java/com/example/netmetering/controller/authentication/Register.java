package com.example.netmetering.controller.authentication;

import com.example.netmetering.IAM.user_details.CustomUserDetailsService;
import com.example.netmetering.dto.UserDTO;
import com.example.netmetering.entities.User;
import com.example.netmetering.entities.UserRegister;
import com.example.netmetering.exception.AccountException;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Register {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegister register){
        User user = new User(
                register.getGiven_name(),
                register.getFamily_name(),
                register.getEmail(),
                register.getPassword(),
                register.getLocation()
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        if (userDetails == null){
            // If user doesn't exist, create one
            userService.createUser(user);
            return ResponseEntity.ok(true);
        }
        // If exist
        return ResponseEntity.badRequest().body("User already exists!");
    }
}
