package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tuteurs")
public class TuteurUiController {

    private final TuteurService tuteurService;

    public TuteurUiController(TuteurService tuteurService) {
        this.tuteurService = tuteurService;
    }

    @GetMapping
    public String showListTuteurs(Model model) {
        List<Tuteur> listTuteurs = tuteurService.getAllTuteur();
        model.addAttribute("listTuteurs", listTuteurs);
        return "tuteur/list";
    }

    @GetMapping("/{id}")
    public String showDetailsTuteur(@PathVariable Integer id,  Model model) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurById(id);
        model.addAttribute("tuteur", tuteur.orElseThrow(() -> new IllegalStateException("Cet tuteur n'existe pas")));
        return "tuteur/detail";
    }

    @GetMapping("/new")
    public String addNewTuteur(Model model) {
        model.addAttribute("tuteur", new Tuteur());
        model.addAttribute("id", null);
        return "tuteur/form";
    }

    @PostMapping("/update")
    public String createTuteur(@RequestBody Tuteur tuteur) {
        tuteurService.addTuteur(tuteur);
        return "redirect:/tuteurs/"+tuteur.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteTuteur(@PathVariable Integer id) {
        tuteurService.deleteTuteur(id);
        return "redirect:/tuteurs/";
    }

    @GetMapping("/{id}/edit")
    public String editTuteur(@PathVariable Integer id,  Model model) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurById(id);
        model.addAttribute("tuteur", tuteur.orElse(null));
        model.addAttribute("id", tuteur.isPresent() ? id :  null);

        return "tuteur/form";
    }

    @PostMapping("/update/{id}")
    public String updateTuteur(@PathVariable Integer id) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurById(id);
        if(tuteur.isPresent()) {
            tuteurService.updateTuteur(id, tuteur.get());
        }
        else{
            return "error/404";
        }

        return "redirect:/tuteurs/"+id;
    }
}
