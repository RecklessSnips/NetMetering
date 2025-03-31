package com.example.netmetering.controller.authentication;

import com.example.netmetering.IAM.authentication.AuthAuthentication;
import com.example.netmetering.IAM.authentication.AuthManager;
import com.example.netmetering.IAM.user_details.CustomUserDetails;
import com.example.netmetering.dto.LoginDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Login {

    @Autowired
    private AuthManager authManager;

    /*
    This Rest API login will not entering spring's AuthenticationSuccessHandler，
    Because AuthenticationSuccessHandler is used for Spring Security
    to handle the successful login for formLogin() or oauth2Login()
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

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout successful");
    }
}
