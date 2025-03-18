package com.example.netmetering.IAM.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthManager implements AuthenticationManager {

    @Autowired
    private AuthProvider authProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authProvider.supports(authentication.getClass())){
            return authProvider.authenticate(authentication);
        }
        // 未被验证的
        throw new RuntimeException("Unmatched authentication type with Identity provider");
    }
}
