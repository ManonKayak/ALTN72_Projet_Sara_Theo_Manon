package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/visites")
public class VisiteUiController {

    private final VisiteReadService readService;
    private final VisiteWriteService writeService;

    public VisiteUiController(VisiteReadService readService, VisiteWriteService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String q,
                       Model model) {
        var res = readService.list(page, size, q);
        model.addAttribute("page", res);
        model.addAttribute("query", q);
        model.addAttribute("pageTitle", "Visites");
        return "visite/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("v", opt.get());
        model.addAttribute("pageTitle", "Détail visite");
        return "visite/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new VisiteForm(null, null, "", null, null));
        model.addAttribute("pageTitle", "Nouvelle visite");
        return "visite/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") VisiteForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouvelle visite");
            return "visite/form";
        }
        var created = writeService.create(form);
        return "redirect:/visites/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var v = opt.get();
        model.addAttribute("form", new VisiteForm(
                v.date(), v.format(), v.commentaire(), null, null
        ));
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier visite");
        return "visite/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") VisiteForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("pageTitle", "Modifier visite");
            return "visite/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/visites/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/visites";
    }
}
