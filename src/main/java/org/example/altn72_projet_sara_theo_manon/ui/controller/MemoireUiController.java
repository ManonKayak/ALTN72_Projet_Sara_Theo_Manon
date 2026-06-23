package org.example.altn72_projet_sara_theo_manon.ui.controller;

import lombok.Data;
import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/memoires")
public class MemoireUiController {

    private final MemoireService memoireService;
    private final String memoireStr = "memoire";

    public MemoireUiController(MemoireService memoireService) {
        this.memoireService = memoireService;
    }

    @Data
    public static class MemoireFormDto {
        private String sujet;
        private Float note;
        private String commentaire;
        private Integer dateSoutenance;
        private Float noteSoutenance;
        private String commentaireSoutenance;
    }

    private static MemoireFormDto fromEntity(Memoire m) {
        MemoireFormDto dto = new MemoireFormDto();
        dto.setSujet(m.getSujet());
        dto.setNote(m.getNote());
        dto.setCommentaire(m.getCommentaire());
        dto.setDateSoutenance(m.getDateSoutenance());
        dto.setNoteSoutenance(m.getNoteSoutenance());
        dto.setCommentaireSoutenance(m.getCommentaireSoutenance());
        return dto;
    }

    private static Memoire toEntity(MemoireFormDto dto) {
        Memoire m = new Memoire();
        m.setSujet(dto.getSujet());
        m.setNote(dto.getNote());
        m.setCommentaire(dto.getCommentaire());
        m.setDateSoutenance(dto.getDateSoutenance());
        m.setNoteSoutenance(dto.getNoteSoutenance());
        m.setCommentaireSoutenance(dto.getCommentaireSoutenance());
        return m;
    }

    @GetMapping
    public String showListMemoires(Model model) {
        List<Memoire> listMemoires = memoireService.getAllMemoire();
        model.addAttribute("listMemoires", listMemoires);
        return "memoire/list";
    }

    @GetMapping("/{id}")
    public String showDetailsMemoire(@PathVariable Integer id, Model model) {
        Optional<Memoire> memoire = memoireService.getMemoireById(id);
        model.addAttribute(memoireStr, memoire.orElseThrow(() -> new IllegalStateException("Cet memoire n'existe pas")));
        return "memoire/detail";
    }

    @GetMapping("/new")
    public String addNewMemoire(Model model) {
        model.addAttribute(memoireStr, new MemoireFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "memoires/update");
        return "memoire/form";
    }

    @PostMapping("/update")
    public String createMemoire(@ModelAttribute MemoireFormDto dto) {
        Memoire saved = memoireService.addMemoire(toEntity(dto));
        return "redirect:/memoires/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteMemoire(@PathVariable Integer id) {
        memoireService.deleteMemoire(id);
        return "redirect:/memoires";
    }

    @GetMapping("/{id}/edit")
    public String editMemoire(@PathVariable Integer id, Model model) {
        Optional<Memoire> memoire = memoireService.getMemoireById(id);
        model.addAttribute(memoireStr, memoire.map(MemoireUiController::fromEntity).orElse(null));
        model.addAttribute("id", memoire.isPresent() ? id : null);
        model.addAttribute("formAction", memoire.isPresent() ? "memoires/update/" + id : "memoires/update");
        return "memoire/form";
    }

    @PostMapping("/update/{id}")
    public String updateMemoire(@PathVariable Integer id, @ModelAttribute MemoireFormDto dto) {
        memoireService.updateMemoire(id, toEntity(dto));
        return "redirect:/memoires/" + id;
    }
}
