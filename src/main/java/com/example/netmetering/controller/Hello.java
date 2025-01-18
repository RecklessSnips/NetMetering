package com.example.netmetering.controller;

import com.example.netmetering.entities.User;
import com.example.netmetering.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class Hello {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome!", HttpStatus.OK);
    }

    @GetMapping("/user")
    public OAuth2AuthenticationToken hello(OAuth2AuthenticationToken authToken){
        OAuth2User principal = authToken.getPrincipal();
        if(principal instanceof DefaultOidcUser oidcUser){
            System.out.println(oidcUser);
            Map<String, Object> claims = oidcUser.getClaims();
            claims.forEach((r, c) -> {
                System.out.println(r);
                System.out.println(c);
            });

        }
        return authToken;
    }

    @GetMapping("/api/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();  // 清除认证信息
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // 销毁会话
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);  // 若启用 HTTPS，这里改为 true
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout successful");
    }
}
