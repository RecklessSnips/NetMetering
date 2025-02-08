package com.example.netmetering.controller;

import com.example.netmetering.dto.UserDTO;
import com.example.netmetering.entities.User;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public UserDTO getCurrentUser(){
        // Get current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser currentUser = (DefaultOidcUser)authentication.getPrincipal();
        String email = currentUser.getEmail();
        User user = userService.findUserByEmail(email);
        return new UserDTO(user);
    }
}
