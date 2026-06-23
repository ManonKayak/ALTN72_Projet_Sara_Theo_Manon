package org.example.altn72_projet_sara_theo_manon.ui.controller;

import lombok.Data;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tuteurs")
public class TuteurUiController {

    private final TuteurService tuteurService;
    private final String tuteurStr = "tuteur";

    public TuteurUiController(TuteurService tuteurService) {
        this.tuteurService = tuteurService;
    }

    @Data
    public static class TuteurFormDto {
        private String poste;
        private String nom;
        private String prenom;
        private String email;
        private String telephone;
        private String remarques;
    }

    private static TuteurFormDto fromEntity(Tuteur t) {
        TuteurFormDto dto = new TuteurFormDto();
        dto.setPoste(t.getPoste());
        dto.setNom(t.getNom());
        dto.setPrenom(t.getPrenom());
        dto.setEmail(t.getEmail());
        dto.setTelephone(t.getTelephone());
        dto.setRemarques(t.getRemarques());
        return dto;
    }

    private static Tuteur toEntity(TuteurFormDto dto) {
        Tuteur t = new Tuteur();
        t.setPoste(dto.getPoste());
        t.setNom(dto.getNom());
        t.setPrenom(dto.getPrenom());
        t.setEmail(dto.getEmail());
        t.setTelephone(dto.getTelephone());
        t.setRemarques(dto.getRemarques());
        return t;
    }

    @GetMapping
    public String showListTuteurs(Model model) {
        List<Tuteur> listTuteurs = tuteurService.getAllTuteur();
        model.addAttribute("listTuteurs", listTuteurs);
        return "tuteur/list";
    }

    @GetMapping("/{id}")
    public String showDetailsTuteur(@PathVariable Integer id, Model model) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurById(id);
        model.addAttribute(tuteurStr, tuteur.orElseThrow(() -> new IllegalStateException("Cet tuteur n'existe pas")));
        return "tuteur/detail";
    }

    @GetMapping("/new")
    public String addNewTuteur(Model model) {
        model.addAttribute(tuteurStr, new TuteurFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "tuteurs/update");
        return "tuteur/form";
    }

    @PostMapping("/update")
    public String createTuteur(@ModelAttribute TuteurFormDto dto) {
        Tuteur saved = tuteurService.addTuteur(toEntity(dto));
        return "redirect:/tuteurs/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteTuteur(@PathVariable Integer id) {
        tuteurService.deleteTuteur(id);
        return "redirect:/tuteurs";
    }

    @GetMapping("/{id}/edit")
    public String editTuteur(@PathVariable Integer id, Model model) {
        Optional<Tuteur> tuteur = tuteurService.getTuteurById(id);
        model.addAttribute(tuteurStr, tuteur.map(TuteurUiController::fromEntity).orElse(null));
        model.addAttribute("id", tuteur.isPresent() ? id : null);
        model.addAttribute("formAction", tuteur.isPresent() ? "tuteurs/update/" + id : "tuteurs/update");
        return "tuteur/form";
    }

    @PostMapping("/update/{id}")
    public String updateTuteur(@PathVariable Integer id, @ModelAttribute TuteurFormDto dto) {
        tuteurService.updateTuteur(id, toEntity(dto));
        return "redirect:/tuteurs/" + id;
    }
}
