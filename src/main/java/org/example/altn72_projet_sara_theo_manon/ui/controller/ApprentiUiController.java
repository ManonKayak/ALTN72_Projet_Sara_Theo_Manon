package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiUiController {

    private final ApprentiService apprentiService;

    public ApprentiUiController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping
    public String showListApprentis(Model model) {
        List<Apprenti> listApprentis = apprentiService.getAllApprenti();
        model.addAttribute("listApprentis", listApprentis);
        return "apprenti/list";
    }

    @GetMapping("/{id}")
    public String showDetailsApprenti(@PathVariable Integer id,  Model model) {
        Optional<Apprenti> apprenti = apprentiService.getApprentiById(id);
        model.addAttribute("apprenti", apprenti.orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas")));
        return "apprenti/detail";
    }

    @GetMapping("/new")
    public String addNewApprenti(Model model) {
        model.addAttribute("apprenti", new Apprenti());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "/apprentis/update");
        return "apprenti/form";
    }

    @PostMapping("/update")
    public String createApprenti(@ModelAttribute Apprenti apprenti) {
        apprentiService.addApprenti(apprenti);
        return "redirect:/apprentis/"+apprenti.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteApprenti(@PathVariable Integer id) {
        apprentiService.deleteApprenti(id);
        return "redirect:/apprentis";
    }

    @GetMapping("/{id}/edit")
    public String editApprenti(@PathVariable Integer id,  Model model) {
        Optional<Apprenti> apprenti = apprentiService.getApprentiById(id);
        model.addAttribute("apprenti", apprenti.orElse(null));
        model.addAttribute("id", apprenti.isPresent() ? id :  null);
        model.addAttribute("formAction", apprenti.isPresent() ? "/apprentis/update/" + id :  "/apprentis/update");

        return "apprenti/form";
    }

    @PostMapping("/update/{id}")
    public String updateApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti) {
        apprentiService.updateApprenti(id, apprenti);
        return "redirect:/apprentis/"+id;
    }
}
