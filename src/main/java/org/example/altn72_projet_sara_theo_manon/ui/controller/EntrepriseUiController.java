package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseReadService;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entreprises")
public class EntrepriseUiController {

    private final EntrepriseReadService readService;
    private final EntrepriseWriteService writeService;

    public EntrepriseUiController(EntrepriseReadService readService, EntrepriseWriteService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String q,
                       Model model) {
        PageResponse<EntrepriseView> res = readService.list(page, size, q);
        model.addAttribute("page", res);
        model.addAttribute("query", q);
        model.addAttribute("pageTitle", "Entreprises");
        return "entreprise/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("e", opt.get());
        model.addAttribute("pageTitle", "Détail entreprise");
        return "entreprise/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new EntrepriseForm("", "", ""));
        model.addAttribute("pageTitle", "Nouvelle entreprise");
        return "entreprise/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") EntrepriseForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouvelle entreprise");
            return "entreprise/form";
        }
        var created = writeService.create(form);
        return "redirect:/entreprises/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var e = opt.get();
        model.addAttribute("form", new EntrepriseForm(e.raisonSociale(), e.adresse(), e.infos()));
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier entreprise");
        return "entreprise/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") EntrepriseForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("pageTitle", "Modifier entreprise");
            return "entreprise/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/entreprises/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/entreprises";
    }
}
