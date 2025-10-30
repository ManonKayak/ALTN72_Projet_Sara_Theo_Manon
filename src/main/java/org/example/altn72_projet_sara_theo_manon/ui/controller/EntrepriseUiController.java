package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entreprises")
public class EntrepriseUiController {

    private final EntrepriseService entrepriseService;

    public EntrepriseUiController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping
    public String showListEntreprises(Model model) {
        List<Entreprise> listEntreprises = entrepriseService.getAllEntreprise();
        model.addAttribute("listEntreprises", listEntreprises);
        return "entreprise/list";
    }

    @GetMapping("/{id}")
    public String showDetailsEntreprise(@PathVariable Integer id,  Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getEntrepriseById(id);
        model.addAttribute("entreprise", entreprise.orElseThrow(() -> new IllegalStateException("Cet entreprise n'existe pas")));
        return "entreprise/detail";
    }

    @GetMapping("/new")
    public String addNewEntreprise(Model model) {
        model.addAttribute("entreprise", new Entreprise());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "entreprises/update");
        return "entreprise/form";
    }

    @PostMapping("/update")
    public String createEntreprise(@RequestBody Entreprise entreprise) {
        entrepriseService.addEntreprise(entreprise);
        return "redirect:/entreprises/"+entreprise.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteEntreprise(@PathVariable Integer id) {
        entrepriseService.deleteEntreprise(id);
        return "redirect:/entreprises/";
    }

    @GetMapping("/{id}/edit")
    public String editEntreprise(@PathVariable Integer id,  Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getEntrepriseById(id);
        model.addAttribute("entreprise", entreprise.orElse(null));
        model.addAttribute("id", entreprise.isPresent() ? id :  null);
        model.addAttribute("formAction", entreprise.isPresent() ? "entreprises/update/" + id :  "entreprises/update");

        return "entreprise/form";
    }

    @PostMapping("/update/{id}")
    public String updateEntreprise(@PathVariable Integer id) {
        Optional<Entreprise> entreprise = entrepriseService.getEntrepriseById(id);
        if(entreprise.isPresent()) {
            entrepriseService.updateEntreprise(id, entreprise.get());
        }
        else{
            return "error/404";
        }

        return "redirect:/entreprises/"+id;
    }
}
