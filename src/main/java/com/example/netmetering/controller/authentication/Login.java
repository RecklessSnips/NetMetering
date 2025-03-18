package com.example.netmetering.controller.authentication;

import com.example.netmetering.IAM.authentication.AuthAuthentication;
import com.example.netmetering.IAM.authentication.AuthManager;
import com.example.netmetering.IAM.user_details.CustomUserDetails;
import com.example.netmetering.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Login {

    @Autowired
    private AuthManager authManager;

    /*
    该 REST API 登录认证，不会进入到 spring 的 AuthenticationSuccessHandler，
    因为 AuthenticationSuccessHandler 主要用于 Spring Security
    处理 formLogin() 或 oauth2Login() 的成功认证
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest,
                                HttpServletRequest request){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // Convert to user details for easy authentication protocol
        CustomUserDetails userDetails = new CustomUserDetails(email, password);
        // Convert to custom authentication
        AuthAuthentication authAuthentication = new AuthAuthentication(userDetails);
        // Using manager to authenticate
        try{
            Authentication authentication = authManager.authenticate(authAuthentication);
            if (authentication.isAuthenticated()){
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                // Returns the current session associated with the request
                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
                return ResponseEntity.ok(true);
            }
        }catch (BadCredentialsException | UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Cannot log you in");
    }
}
