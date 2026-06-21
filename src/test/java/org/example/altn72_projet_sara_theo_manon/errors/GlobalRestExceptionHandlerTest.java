package org.example.altn72_projet_sara_theo_manon.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalRestExceptionHandlerTest {

    private GlobalRestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalRestExceptionHandler();
    }

    @Test
    void handleNotFound_returns404WithMessage() {
        ResponseEntity<ProblemDetail> response = handler.handleNotFound(new NotFoundException("Apprenti id=99 introuvable"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ressource introuvable", response.getBody().getTitle());
        assertEquals("Apprenti id=99 introuvable", response.getBody().getDetail());
    }

    @Test
    void handleBadRequest_returns400WithMessage() {
        ResponseEntity<ProblemDetail> response = handler.handleBadRequest(new BadRequestException("Entreprise 99 inexistante"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Requête invalide", response.getBody().getTitle());
        assertEquals("Entreprise 99 inexistante", response.getBody().getDetail());
    }

    @Test
    void handleBadJson_returns400() {
        ResponseEntity<ProblemDetail> response = handler.handleBadJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Corps JSON illisible", response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
    }

    @Test
    void handleMissingParam_returns400WithParamName() {
        var ex = new MissingServletRequestParameterException("page", "Integer");
        ResponseEntity<ProblemDetail> response = handler.handleMissingParam(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Paramètre manquant", response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
        assertTrue(response.getBody().getDetail().contains("page"));
    }

    @Test
    void handleTypeMismatch_returns400WithParamName() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("id");
        when(ex.getRequiredType()).thenReturn((Class) Integer.class);
        ResponseEntity<ProblemDetail> response = handler.handleTypeMismatch(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Type de paramètre invalide", response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
        assertTrue(response.getBody().getDetail().contains("id"));
        assertTrue(response.getBody().getDetail().contains("Integer"));
    }

    @Test
    void handleTypeMismatch_withNullRequiredType_returns400() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("id");
        when(ex.getRequiredType()).thenReturn(null);
        ResponseEntity<ProblemDetail> response = handler.handleTypeMismatch(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getDetail());
        assertTrue(response.getBody().getDetail().contains("?"));
    }

    @Test
    void handleIntegrity_returns409() {
        ResponseEntity<ProblemDetail> response = handler.handleIntegrity();
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
    }

    @Test
    void handleAccessDenied_returns403() {
        ResponseEntity<ProblemDetail> response = handler.handleAccessDenied();
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Accès refusé", response.getBody().getTitle());
    }

    @Test
    void handleBeanValidation_returns400WithFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(
                new FieldError("apprenti", "nom", "ne doit pas être vide")
        ));
        ResponseEntity<ProblemDetail> response = handler.handleBeanValidation(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation échouée", response.getBody().getTitle());
        assertNotNull(response.getBody().getProperties());
        Map<?, ?> errors = (Map<?, ?>) response.getBody().getProperties().get("errors");
        assertNotNull(errors);
        assertEquals("ne doit pas être vide", errors.get("nom"));
    }

    @Test
    void handleBeanValidation_withNonFieldError_usesObjectName() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        var objectError = mock(org.springframework.validation.ObjectError.class);
        when(objectError.getObjectName()).thenReturn("apprenti");
        when(objectError.getDefaultMessage()).thenReturn("objet invalide");
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(objectError));
        ResponseEntity<ProblemDetail> response = handler.handleBeanValidation(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getProperties());
        Map<?, ?> errors = (Map<?, ?>) response.getBody().getProperties().get("errors");
        assertEquals("objet invalide", errors.get("apprenti"));
    }

    @Test
    void handleUnknown_returns500() {
        ResponseEntity<ProblemDetail> response = handler.handleUnknown(new RuntimeException("crash inattendu"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erreur interne", response.getBody().getTitle());
        assertNotNull(response.getBody().getDetail());
    }

    @Test
    void problemDetail_containsTimestampAndService() {
        ResponseEntity<ProblemDetail> response = handler.handleNotFound(new NotFoundException("test"));
        assertNotNull(response.getBody());
        Map<String, Object> props = response.getBody().getProperties();
        assertNotNull(props);
        assertNotNull(props.get("timestamp"));
        assertEquals("altn72-backend", props.get("service"));
    }
}
