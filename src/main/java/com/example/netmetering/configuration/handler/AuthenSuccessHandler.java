package com.example.netmetering.configuration.handler;

import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.UserRepository;
import com.example.netmetering.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    final String URI = "http://localhost:5173";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Get unprocessed request
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        Object principal = authentication.getPrincipal();
        System.out.println("Authentication: ");
        System.out.println(authentication);
        System.out.println(authentication.getName());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.isAuthenticated());

        OAuth2User oAuth2User = null;
        if(principal instanceof DefaultOidcUser){
            // Google user
            System.out.println("Google");
            oAuth2User = (DefaultOidcUser) principal;
        }else if(principal instanceof DefaultOAuth2User){
            // Facebook user
            System.out.println("Facebook");
            oAuth2User = (DefaultOAuth2User) principal;
        }

        String email = oAuth2User.getAttribute("email");
        Optional<User> op = userRepository.findByEmail(email);
        // If exists, use that account, if not, register a new one
        boolean present = op.isPresent();
        if(!present){
            registerUser(oAuth2User);
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

    private void registerUser(OAuth2User oauth2User) {
        User user = null;
        if(oauth2User instanceof DefaultOidcUser){
            // User from Google
            DefaultOidcUser googleUser = (DefaultOidcUser) oauth2User;
            // Oauth2 user doesn't have a password in our database
            user = new User(googleUser.getGivenName(), googleUser.getFamilyName(),
                    googleUser.getEmail(), "", null);
        }else if (oauth2User instanceof DefaultOAuth2User){
            DefaultOAuth2User faceBookUser = (DefaultOAuth2User) oauth2User;
            String name = faceBookUser.getAttribute("name");
            String email = faceBookUser.getAttribute("email");
            String[] split = name.split(" ");
            String givenName = split[0];
            String familyName = split[1];
            // Oauth2 user doesn't have a password in our database
            user = new User(givenName, familyName,
                    email,"", null);
        }

        // Register user
        userService.createUser(user);
    }
}
