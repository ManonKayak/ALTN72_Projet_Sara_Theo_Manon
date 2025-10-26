package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeUiController {
    @GetMapping("/")
    public String home() {
        // Renvoie le template templates/home/index.html
        return "home/index";
    }
}
