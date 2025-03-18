package com.example.netmetering.IAM.authentication;

import com.example.netmetering.IAM.user_details.CustomUserDetails;
import com.example.netmetering.IAM.user_details.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // No need to cast, just want to make it clear for myself...
        AuthAuthentication authAuthentication = (AuthAuthentication) authentication;
        CustomUserDetails userDetails = (CustomUserDetails) authAuthentication.getPrincipal();

        // Real user in the database
        UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUsername());
        // If the user doesn't exist
        if(user == null){
            throw new UsernameNotFoundException("User doesn't existtttt");
        }
        // Compare with the user that attempt to logging in
        if (user.getPassword().equals(authAuthentication.getCredentials())){
            System.out.println("Password correct!");
            authAuthentication.setAuthenticated(true);
            return authAuthentication;
        }else{
            System.out.println("Wrong password!");
            throw new BadCredentialsException("Wrong Credentials!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthAuthentication.class.equals(authentication);
    }
}
