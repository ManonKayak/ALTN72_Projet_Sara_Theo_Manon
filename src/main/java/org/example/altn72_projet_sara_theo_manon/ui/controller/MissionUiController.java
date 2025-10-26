package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/missions")
public class MissionUiController {

    private final MissionReadService readService;
    private final MissionWriteService writeService;

    public MissionUiController(MissionReadService readService, MissionWriteService writeService) {
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
        model.addAttribute("pageTitle", "Missions");
        return "mission/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("m", opt.get());
        model.addAttribute("pageTitle", "Détail mission");
        return "mission/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new MissionForm("", "", ""));
        model.addAttribute("pageTitle", "Nouvelle mission");
        return "mission/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") MissionForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouvelle mission");
            return "mission/form";
        }
        var created = writeService.create(form);
        return "redirect:/missions/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var m = opt.get();
        model.addAttribute("form", new MissionForm(m.motsCles(), m.metierCible(), m.commentaires()));
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier mission");
        return "mission/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") MissionForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("pageTitle", "Modifier mission");
            return "mission/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/missions/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/missions";
    }
}
