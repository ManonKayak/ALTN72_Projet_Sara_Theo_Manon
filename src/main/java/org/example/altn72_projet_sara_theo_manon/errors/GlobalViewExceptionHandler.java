package org.example.altn72_projet_sara_theo_manon.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler des exceptions pour les contrôleurs MVC (package ...web).
 * Renvoie des templates Thymeleaf d'erreur.
 */
@ControllerAdvice(basePackages = "org.example.altn72_projet_sara_theo_manon.web")
public class GlobalViewExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handle500(Exception ex, Model model) {
        model.addAttribute("error", "Erreur interne.");
        return "error/500";
    }
}
