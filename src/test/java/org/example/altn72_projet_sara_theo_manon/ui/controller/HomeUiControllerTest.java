package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeUiControllerTest {

    @InjectMocks
    private HomeUiController controller;

    @Test
    void home_returnsHomeView() {
        assertEquals("home/index", controller.home());
    }

    @Test
    void login_returnsLoginView() {
        assertEquals("home/login", controller.login());
    }

    @Test
    void logout_redirectsToLogin() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication auth = mock(Authentication.class);
        String view = controller.logout(auth, request, response);
        assertEquals("redirect:home/login", view);
    }
}
