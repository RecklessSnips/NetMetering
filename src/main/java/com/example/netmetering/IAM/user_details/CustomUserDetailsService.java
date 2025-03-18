package com.example.netmetering.IAM.user_details;

import com.example.netmetering.entities.User;
import com.example.netmetering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user != null){
            return new CustomUserDetails(user.getEmail(), user.getPassword());
        }
        return null;
    }
}
