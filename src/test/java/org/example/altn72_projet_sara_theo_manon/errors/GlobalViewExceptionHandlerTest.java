package org.example.altn72_projet_sara_theo_manon.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

class GlobalViewExceptionHandlerTest {

    private GlobalViewExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalViewExceptionHandler();
    }

    @Test
    void handleNotFound_returns404ViewWithMessage() {
        Model model = new ExtendedModelMap();
        String view = handler.handleNotFound(new NotFoundException("Ressource introuvable"), model);
        assertEquals("error/404", view);
        assertEquals("Ressource introuvable", model.asMap().get("error"));
    }

    @Test
    void handle500_returns500ViewWithGenericMessage() {
        Model model = new ExtendedModelMap();
        String view = handler.handle500(new RuntimeException("crash"), model);
        assertEquals("error/500", view);
        assertNotNull(model.asMap().get("error"));
    }
}
