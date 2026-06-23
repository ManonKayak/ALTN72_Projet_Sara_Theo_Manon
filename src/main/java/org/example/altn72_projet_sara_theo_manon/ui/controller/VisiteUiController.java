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
    private final String visiteStr = "visite";

    public VisiteUiController(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    public static class VisiteFormDto {
        private Integer date;
        private Integer format;
        private String commentaire;

        public Integer getDate() { return date; }
        public void setDate(Integer date) { this.date = date; }
        public Integer getFormat() { return format; }
        public void setFormat(Integer format) { this.format = format; }
        public String getCommentaire() { return commentaire; }
        public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    }

    private static VisiteFormDto fromEntity(Visite v) {
        VisiteFormDto dto = new VisiteFormDto();
        dto.setDate(v.getDate());
        dto.setFormat(v.getFormat());
        dto.setCommentaire(v.getCommentaire());
        return dto;
    }

    private static Visite toEntity(VisiteFormDto dto) {
        Visite v = new Visite();
        v.setDate(dto.getDate());
        v.setFormat(dto.getFormat());
        v.setCommentaire(dto.getCommentaire());
        return v;
    }

    @GetMapping
    public String showListVisites(Model model) {
        List<Visite> listVisites = visiteService.getAllVisite();
        model.addAttribute("listVisites", listVisites);
        return "visite/list";
    }

    @GetMapping("/{id}")
    public String showDetailsVisite(@PathVariable Integer id, Model model) {
        Optional<Visite> visite = visiteService.getVisiteById(id);
        model.addAttribute(visiteStr, visite.orElseThrow(() -> new IllegalStateException("Cet visite n'existe pas")));
        return "visite/detail";
    }

    @GetMapping("/new")
    public String addNewVisite(Model model) {
        model.addAttribute(visiteStr, new VisiteFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "visites/update");
        return "visite/form";
    }

    @PostMapping("/update")
    public String createVisite(@ModelAttribute VisiteFormDto dto) {
        Visite saved = visiteService.addVisite(toEntity(dto));
        return "redirect:/visites/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteVisite(@PathVariable Integer id) {
        visiteService.deleteVisite(id);
        return "redirect:/visites";
    }

    @GetMapping("/{id}/edit")
    public String editVisite(@PathVariable Integer id, Model model) {
        Optional<Visite> visite = visiteService.getVisiteById(id);
        model.addAttribute(visiteStr, visite.map(VisiteUiController::fromEntity).orElse(null));
        model.addAttribute("id", visite.isPresent() ? id : null);
        model.addAttribute("formAction", visite.isPresent() ? "visites/update/" + id : "visites/update");
        return "visite/form";
    }

    @PostMapping("/update/{id}")
    public String updateVisite(@PathVariable Integer id, @ModelAttribute VisiteFormDto dto) {
        visiteService.updateVisite(id, toEntity(dto));
        return "redirect:/visites/" + id;
    }
}
