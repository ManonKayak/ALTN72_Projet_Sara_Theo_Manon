package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apprentis")
public class ApprentiUiController {

    private final ApprentiReadService readService;
    private final ApprentiWriteService writeService;

    public ApprentiUiController(ApprentiReadService readService, ApprentiWriteService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String q,
                       Model model) {
        PageResponse<ApprentiView> res = readService.list(page, size, q);
        model.addAttribute("page", res);
        model.addAttribute("query", q);
        model.addAttribute("pageTitle", "Apprentis");
        return "apprenti/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("apprenti", opt.get());
        model.addAttribute("pageTitle", "Détail apprenti");
        return "apprenti/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new ApprentiForm(2025, 1, "", "", "", "", 1, false));
        model.addAttribute("pageTitle", "Nouvel apprenti");
        return "apprenti/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") ApprentiForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouvel apprenti");
            return "apprenti/form";
        }
        var created = writeService.create(form);
        return "redirect:/apprentis/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var a = opt.get();

        // scission "Prenom Nom"
        var parts = a.nomComplet().split("\\s+", 2);
        var prenom = parts.length > 0 ? parts[0] : "";
        var nom = parts.length > 1 ? parts[1] : "";

        var form = new ApprentiForm(
                a.anneeAcademique(), a.majeure(),
                nom, prenom,
                a.mail(), a.telephone(), a.niveau(), a.archive()
        );
        model.addAttribute("form", form);
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier apprenti");
        return "apprenti/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") ApprentiForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Modifier apprenti");
            model.addAttribute("id", id);
            return "apprenti/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/apprentis/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/apprentis";
    }
}
