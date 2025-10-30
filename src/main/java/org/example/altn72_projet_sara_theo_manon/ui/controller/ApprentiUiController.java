package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/apprentis")
public class ApprentiUiController {

    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;
    private final MissionService missionService;
    private final TuteurService tuteurService;
    private final MemoireService memoireService;

    public ApprentiUiController(ApprentiService apprentiService, EntrepriseService entrepriseService, MissionService missionService,
                                TuteurService tuteurService, MemoireService memoireService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.missionService = missionService;
        this.tuteurService = tuteurService;
        this.memoireService = memoireService;
    }

    @GetMapping
    public String showListApprentis(Model model) {
        List<Apprenti> listApprentis = apprentiService.getAllApprentiActifs();
        model.addAttribute("listApprentis", listApprentis);
        model.addAttribute("success", "");
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

        model.addAttribute("entreprises", entrepriseService.getAllEntreprise());
        model.addAttribute("tuteurs", tuteurService.getAllTuteur());
        model.addAttribute("memoires", memoireService.getAllMemoire());
        model.addAttribute("missions", missionService.getAllMission());

        return "apprenti/form";
    }

    @PostMapping("/update/{id}")
    public String updateApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprenti, Model model) {
        boolean success = apprentiService.updateApprenti(id, apprenti);
        model.addAttribute("success", success ? "Modification réussie !" : "Échec de la modification.");

        return "redirect:/apprentis";
    }

    @GetMapping("/filter")
    public String filterApprenti(
            @RequestParam String type,
            @RequestParam String valeur,
            Model model) {

        List<Apprenti> list = new ArrayList<>();

        switch (type) {
            case "nom":
                list = apprentiService.FilterApprentiByNom(valeur);
                break;
            case "entreprise":
                var entreprise = entrepriseService.getEntrepriseByName(valeur);
                if (entreprise != null) {
                    list = apprentiService.FilterApprentiByEntreprise_Id(entreprise.getId());
                }
                break;
            case "mission":
                var missions = missionService.getMissionByMotsCles(valeur);
                if (!missions.isEmpty()) {
                    list = apprentiService.FilterApprentiByMission(missions.stream().map(Mission::getId).collect(Collectors.toList()));
                }
                break;
            case "annee":
                try {
                    int annee = Integer.parseInt(valeur);
                    list = apprentiService.FilterApprentiByAnneeAcademique(annee);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
        }

//        if (list.isEmpty()) {
//            return "apprenti/list";
//        }

        model.addAttribute("listApprentis", list);
        return "apprenti/list";
    }

}
