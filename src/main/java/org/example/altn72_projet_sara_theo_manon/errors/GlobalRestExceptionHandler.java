package org.example.altn72_projet_sara_theo_manon.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global des exceptions pour les contrôleurs REST (package ...api).
 * Retourne des réponses au format RFC 7807 (Problem Details).
 */
@RestControllerAdvice(basePackages = "org.example.altn72_projet_sara_theo_manon.api")
public class GlobalRestExceptionHandler {

    private ProblemDetail problem(HttpStatus status, String title, String detail, String type) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        if (type != null) pd.setType(URI.create(type));
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("service", "altn72-backend");
        return pd;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NotFoundException ex) {
        var pd = problem(HttpStatus.NOT_FOUND, "Ressource introuvable", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequest(BadRequestException ex) {
        var pd = problem(HttpStatus.BAD_REQUEST, "Requête invalide", ex.getMessage(), null);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleBadJson(HttpMessageNotReadableException ex) {
        var pd = problem(HttpStatus.BAD_REQUEST, "Corps JSON illisible",
                "Le JSON est mal formé ou incompatible avec le modèle.", null);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ProblemDetail> handleMissingParam(MissingServletRequestParameterException ex) {
        var pd = problem(HttpStatus.BAD_REQUEST, "Paramètre manquant",
                "Paramètre requis manquant : " + ex.getParameterName(), null);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var pd = problem(HttpStatus.BAD_REQUEST, "Type de paramètre invalide",
                "Paramètre '" + ex.getName() + "' de type attendu " +
                        (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "?"), null);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleIntegrity(DataIntegrityViolationException ex) {
        var pd = problem(HttpStatus.CONFLICT, "Contrainte d’intégrité violée",
                "La requête viole une contrainte de base de données (clé étrangère, unicité, etc.).", null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDenied(AccessDeniedException ex) {
        var pd = problem(HttpStatus.FORBIDDEN, "Accès refusé",
                "Vous n’avez pas les droits suffisants pour cette ressource.", null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleBeanValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (var error : ex.getBindingResult().getAllErrors()) {
            String field = (error instanceof FieldError fe) ? fe.getField() : error.getObjectName();
            fieldErrors.put(field, error.getDefaultMessage());
        }
        var pd = problem(HttpStatus.BAD_REQUEST, "Validation échouée",
                "Certaines propriétés ne respectent pas les contraintes.", null);
        pd.setProperty("errors", fieldErrors);
        return ResponseEntity.badRequest().body(pd);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ProblemDetail> handleErrorResponse(ErrorResponseException ex) {
        var pd = ex.getBody();
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("service", "altn72-backend");
        return ResponseEntity.status(ex.getStatusCode()).body(pd);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnknown(Exception ex) {
        var pd = problem(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne",
                "Une erreur inattendue est survenue. Réessayez plus tard.", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}
