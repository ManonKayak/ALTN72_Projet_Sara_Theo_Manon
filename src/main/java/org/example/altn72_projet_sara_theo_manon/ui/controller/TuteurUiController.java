package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tuteurs")
public class TuteurUiController {

    private final TuteurReadService readService;
    private final TuteurWriteService writeService;

    public TuteurUiController(TuteurReadService readService, TuteurWriteService writeService) {
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
        model.addAttribute("pageTitle", "Tuteurs");
        return "tuteur/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("t", opt.get());
        model.addAttribute("pageTitle", "Détail tuteur");
        return "tuteur/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new TuteurForm("", "", "", "", "", "", null));
        model.addAttribute("pageTitle", "Nouveau tuteur");
        return "tuteur/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") TuteurForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouveau tuteur");
            return "tuteur/form";
        }
        var created = writeService.create(form);
        return "redirect:/tuteurs/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var t = opt.get();
        model.addAttribute("form", new TuteurForm(
                t.nomComplet().split(" ")[0],
                t.nomComplet().split(" ")[1],
                t.poste(),
                t.email(),
                t.telephone(),
                t.remarques(),
                null
        ));
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier tuteur");
        return "tuteur/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") TuteurForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Modifier tuteur");
            model.addAttribute("id", id);
            return "tuteur/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/tuteurs/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/tuteurs";
    }
}
