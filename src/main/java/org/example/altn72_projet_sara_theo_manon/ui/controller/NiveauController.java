package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.ui.service.NiveauService;
import org.springframework.ui.Model;
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
        model.addAttribute("niveaux", niveaux);
        return "niveau/list";
    }
}
