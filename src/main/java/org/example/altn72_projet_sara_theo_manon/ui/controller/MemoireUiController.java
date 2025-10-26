package org.example.altn72_projet_sara_theo_manon.ui.controller;

import jakarta.validation.Valid;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memoires")
public class MemoireUiController {

    private final MemoireReadService readService;
    private final MemoireWriteService writeService;

    public MemoireUiController(MemoireReadService readService, MemoireWriteService writeService) {
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
        model.addAttribute("pageTitle", "Mémoires");
        return "memoire/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        model.addAttribute("mem", opt.get());
        model.addAttribute("pageTitle", "Détail mémoire");
        return "memoire/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new MemoireForm("", null, null, null, null, null));
        model.addAttribute("pageTitle", "Nouveau mémoire");
        return "memoire/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") MemoireForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("pageTitle", "Nouveau mémoire");
            return "memoire/form";
        }
        var created = writeService.create(form);
        return "redirect:/memoires/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        var opt = readService.byId(id);
        if (opt.isEmpty()) return "error/404";
        var v = opt.get();
        model.addAttribute("form", new MemoireForm(
                v.sujet(), v.note(), v.commentaire(), v.dateSoutenance(), v.noteSoutenance(), v.commentaireSoutenance()
        ));
        model.addAttribute("id", id);
        model.addAttribute("pageTitle", "Modifier mémoire");
        return "memoire/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("form") MemoireForm form,
                         BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("pageTitle", "Modifier mémoire");
            return "memoire/form";
        }
        var updated = writeService.update(id, form);
        return updated.isPresent() ? "redirect:/memoires/" + id : "error/404";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        writeService.delete(id);
        return "redirect:/memoires";
    }
}
