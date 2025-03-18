package com.example.netmetering.exception.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Whenever an AuthenticationException is detected,  the filter will launch AuthenticationEntryPoint
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        Map<String, Object> errorDetails = new HashMap<>();

        /*
         Provide a default way to handle the exception if it's not being
         try/catch, then the error message will have 2 fields: error and message
         But if being try/catch, then the return value is upto the logic!
         */

        if (authException instanceof BadCredentialsException ||
                authException instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorDetails.put("error", "Invalid Credentials");
            errorDetails.put("message", authException.getMessage());
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            errorDetails.put("error", "Unauthorized");
            errorDetails.put("message", authException.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
