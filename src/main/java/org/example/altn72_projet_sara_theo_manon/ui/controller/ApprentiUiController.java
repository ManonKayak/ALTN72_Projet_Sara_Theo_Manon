package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.ui.requests.FilterRequest;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/apprentis")
public class ApprentiUiController {

    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;
    private final MissionService missionService;

    public ApprentiUiController(ApprentiService apprentiService, EntrepriseService entrepriseService, MissionService missionService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.missionService = missionService;
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

    @GetMapping("/filter")
    public String filterApprenti(@RequestBody FilterRequest filterRequest, Model model) {
        List<Apprenti> list = List.of();
        if (!filterRequest.Nom.isEmpty())
        {
            list = apprentiService.FilterApprentiByNom(filterRequest.Nom);
            model.addAttribute("listApprentis", list);

        }
        if (!filterRequest.Entreprise.isEmpty())
        {
            var entreprise = entrepriseService.getEntrepriseByName(filterRequest.Entreprise);
            list = apprentiService.FilterApprentiByEntreprise_Id(entreprise.getId());
            model.addAttribute("listApprentis", list);
        }
        if (!filterRequest.Mission.isEmpty())
        {
            var missions = missionService.getMissionByMotsCles(filterRequest.Mission);
            list = apprentiService.FilterApprentiByMission(missions.stream().map(Mission::getId).collect(Collectors.toList()));
            model.addAttribute("listApprentis", list);
        }
        if (filterRequest.Annee != 0)
        {
            list = apprentiService.FilterApprentiByAnneeAcademique(filterRequest.Annee);
            model.addAttribute("listApprentis", list);
        }

        if (list.isEmpty())
            return "redirect:/apprentis?vide=true";
        return "redirect:/apprentis";
    }
}
