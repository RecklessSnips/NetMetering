package com.example.netmetering.configuration.handler;

import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AuthenSuccessHandler implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();  // Save unprocessed request
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserRepository userRepository;

    final String URI = "http://localhost:5173";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        System.out.println("response: ");
        System.out.println(response.getHeaderNames());
        System.out.println(response.getHeader("Set-Cookie"));
        // Get unprocessed request
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        Optional<User> op = userRepository.findByEmail(email);
        // If exists, use that account, if not, register a new one
        boolean present = op.isPresent();
        if(!present){
            User user = getUser(oidcUser);
            // Register user
            userRepository.save(user);
        }
        // Redirect to the old page (or login page where the User left)
        if (savedRequest != null) {
            // If there's unprocessed request, process it
            String targetUrl = savedRequest.getRedirectUrl();
            if(targetUrl.equals("http://localhost:8080/?continue")) {
                response.sendRedirect(URI + "/dashboard");
                return;
            }
            System.out.println(targetUrl.substring(22));
            String path = targetUrl.substring(23);
            redirectStrategy.sendRedirect(request, response, URI + "/" + path);
        } else {
            // If not, go to the home page
            response.sendRedirect(URI + "/dashboard");
        }
    }

    private User getUser(DefaultOidcUser oidcUser) {
        // User from Google
        User user = new User(oidcUser.getGivenName(), oidcUser.getFamilyName(),
                oidcUser.getEmail(), null);

        // Default Account
        // Use user's email for now
        EnergyAccount account = new EnergyAccount(user, user.getEmail());
        account.setEnergyBalance(new BigDecimal("0"));
        account.setUser(user);

        // JPA relation
        user.setAccount(account);
        return user;
    }
}
