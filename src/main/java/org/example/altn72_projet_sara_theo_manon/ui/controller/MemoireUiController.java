package org.example.altn72_projet_sara_theo_manon.ui.controller;

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

    public MemoireUiController(MemoireService memoireService) {
        this.memoireService = memoireService;
    }

    @GetMapping
    public String showListMemoires(Model model) {
        List<Memoire> listMemoires = memoireService.getAllMemoire();
        model.addAttribute("listMemoires", listMemoires);
        return "memoire/list";
    }

    @GetMapping("/{id}")
    public String showDetailsMemoire(@PathVariable Integer id,  Model model) {
        Optional<Memoire> memoire = memoireService.getMemoireById(id);
        model.addAttribute("memoire", memoire.orElseThrow(() -> new IllegalStateException("Cet memoire n'existe pas")));
        return "memoire/detail";
    }

    @GetMapping("/new")
    public String addNewMemoire(Model model) {
        model.addAttribute("memoire", new Memoire());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "memoires/update");
        return "memoire/form";
    }

    @PostMapping("/update")
    public String createMemoire(@RequestBody Memoire memoire) {
        memoireService.addMemoire(memoire);
        return "redirect:/memoires/"+memoire.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteMemoire(@PathVariable Integer id) {
        memoireService.deleteMemoire(id);
        return "redirect:/memoires/";
    }

    @GetMapping("/{id}/edit")
    public String editMemoire(@PathVariable Integer id,  Model model) {
        Optional<Memoire> memoire = memoireService.getMemoireById(id);
        model.addAttribute("memoire", memoire.orElse(null));
        model.addAttribute("id", memoire.isPresent() ? id :  null);
        model.addAttribute("formAction", memoire.isPresent() ? "memoires/update/" + id :  "memoires/update");

        return "memoire/form";
    }

    @PostMapping("/update/{id}")
    public String updateMemoire(@PathVariable Integer id) {
        Optional<Memoire> memoire = memoireService.getMemoireById(id);
        if(memoire.isPresent()) {
            memoireService.updateMemoire(id, memoire.get());
        }
        else{
            return "error/404";
        }

        return "redirect:/memoires/"+id;
    }
}
