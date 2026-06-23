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
    private final String entrepriseStr = "entreprise";

    public EntrepriseUiController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    public static class EntrepriseFormDto {
        private String raisonSociale;
        private String adresse;
        private String infos;

        public String getRaisonSociale() { return raisonSociale; }
        public void setRaisonSociale(String raisonSociale) { this.raisonSociale = raisonSociale; }
        public String getAdresse() { return adresse; }
        public void setAdresse(String adresse) { this.adresse = adresse; }
        public String getInfos() { return infos; }
        public void setInfos(String infos) { this.infos = infos; }
    }

    private static EntrepriseFormDto fromEntity(Entreprise e) {
        EntrepriseFormDto dto = new EntrepriseFormDto();
        dto.setRaisonSociale(e.getRaisonSociale());
        dto.setAdresse(e.getAdresse());
        dto.setInfos(e.getInfos());
        return dto;
    }

    private static Entreprise toEntity(EntrepriseFormDto dto) {
        Entreprise e = new Entreprise();
        e.setRaisonSociale(dto.getRaisonSociale());
        e.setAdresse(dto.getAdresse());
        e.setInfos(dto.getInfos());
        return e;
    }

    @GetMapping
    public String showListEntreprises(Model model) {
        List<Entreprise> listEntreprises = entrepriseService.getAllEntreprise();
        model.addAttribute("listEntreprises", listEntreprises);
        return "entreprise/list";
    }

    @GetMapping("/{id}")
    public String showDetailsEntreprise(@PathVariable Integer id, Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getEntrepriseById(id);
        model.addAttribute(entrepriseStr, entreprise.orElseThrow(() -> new IllegalStateException("Cet entreprise n'existe pas")));
        return "entreprise/detail";
    }

    @GetMapping("/new")
    public String addNewEntreprise(Model model) {
        model.addAttribute(entrepriseStr, new EntrepriseFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "entreprises/update");
        return "entreprise/form";
    }

    @PostMapping("/update")
    public String createEntreprise(@ModelAttribute EntrepriseFormDto dto) {
        Entreprise saved = entrepriseService.addEntreprise(toEntity(dto));
        return "redirect:/entreprises/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteEntreprise(@PathVariable Integer id) {
        entrepriseService.deleteEntreprise(id);
        return "redirect:/entreprises";
    }

    @GetMapping("/{id}/edit")
    public String editEntreprise(@PathVariable Integer id, Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getEntrepriseById(id);
        model.addAttribute(entrepriseStr, entreprise.map(EntrepriseUiController::fromEntity).orElse(null));
        model.addAttribute("id", entreprise.isPresent() ? id : null);
        model.addAttribute("formAction", entreprise.isPresent() ? "entreprises/update/" + id : "entreprises/update");
        return "entreprise/form";
    }

    @PostMapping("/update/{id}")
    public String updateEntreprise(@PathVariable Integer id, @ModelAttribute EntrepriseFormDto dto) {
        entrepriseService.updateEntreprise(id, toEntity(dto));
        return "redirect:/entreprises/" + id;
    }
}
