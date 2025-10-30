package org.example.altn72_projet_sara_theo_manon.ui.controller;

import ch.qos.logback.core.model.Model;
import org.example.altn72_projet_sara_theo_manon.model.NiveauRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.NiveauService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/niveau")
public class NiveauController {
    private final NiveauService niveauService;

    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    @GetMapping("/all")
    public String niveau(Model model){
        var niveaux = niveauService.GetNiveaux();
        return "niveau";
    }
}
