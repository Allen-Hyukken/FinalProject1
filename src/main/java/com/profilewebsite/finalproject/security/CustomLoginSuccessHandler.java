package com.profilewebsite.finalproject.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = "/";

        for (GrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals("TEACHER")) {
                redirectURL = "/teacher";
                break;
            } else if (auth.getAuthority().equals("STUDENT")) {
                redirectURL = "/student";
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
