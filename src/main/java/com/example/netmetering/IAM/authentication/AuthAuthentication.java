package com.example.netmetering.IAM.authentication;

import com.example.netmetering.IAM.user_details.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthAuthentication implements Authentication {

    private CustomUserDetails userDetails;

    private boolean ifAuthenticated;

    public AuthAuthentication(CustomUserDetails userDetails){
        this.userDetails = userDetails;
        // When loading a user the first time, set the authenticated to false
        this.ifAuthenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return this.ifAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.ifAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
