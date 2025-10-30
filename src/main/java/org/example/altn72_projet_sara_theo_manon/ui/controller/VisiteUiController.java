package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/visites")
public class VisiteUiController {

    private final VisiteService visiteService;

    public VisiteUiController(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    @GetMapping
    public String showListVisites(Model model) {
        List<Visite> listVisites = visiteService.getAllVisite();
        model.addAttribute("listVisites", listVisites);
        return "visite/list";
    }

    @GetMapping("/{id}")
    public String showDetailsVisite(@PathVariable Integer id,  Model model) {
        Optional<Visite> visite = visiteService.getVisiteById(id);
        model.addAttribute("visite", visite.orElseThrow(() -> new IllegalStateException("Cet visite n'existe pas")));
        return "visite/detail";
    }

    @GetMapping("/new")
    public String addNewVisite(Model model) {
        model.addAttribute("visite", new Visite());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "visites/update");

        return "visite/form";
    }

    @PostMapping("/update")
    public String createVisite(@RequestBody Visite visite) {
        visiteService.addVisite(visite);
        return "redirect:/visites/"+visite.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteVisite(@PathVariable Integer id) {
        visiteService.deleteVisite(id);
        return "redirect:/visites/";
    }

    @GetMapping("/{id}/edit")
    public String editVisite(@PathVariable Integer id,  Model model) {
        Optional<Visite> visite = visiteService.getVisiteById(id);
        model.addAttribute("visite", visite.orElse(null));
        model.addAttribute("id", visite.isPresent() ? id :  null);
        model.addAttribute("formAction", visite.isPresent() ? "visites/update/" + id :  "visites/update");

        return "visite/form";
    }

    @PostMapping("/update/{id}")
    public String updateVisite(@PathVariable Integer id) {
        Optional<Visite> visite = visiteService.getVisiteById(id);
        if(visite.isPresent()) {
            visiteService.updateVisite(id, visite.get());
        }
        else{
            return "error/404";
        }

        return "redirect:/visites/"+id;
    }
}
