package org.example.altn72_projet_sara_theo_manon.login_security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthHandler implements AuthenticationFailureHandler {

    public CustomAuthHandler() {}

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        try {
            response.sendRedirect("/login?error=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
