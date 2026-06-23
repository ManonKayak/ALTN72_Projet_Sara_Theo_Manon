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

@Controller
@RequestMapping("/apprentis")
public class ApprentiUiController {

    private final ApprentiService apprentiService;
    private final EntrepriseService entrepriseService;
    private final MissionService missionService;
    private final TuteurService tuteurService;
    private final MemoireService memoireService;

    private final String apprentiStr = "apprenti";

    public ApprentiUiController(ApprentiService apprentiService, EntrepriseService entrepriseService, MissionService missionService,
                                TuteurService tuteurService, MemoireService memoireService) {
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.missionService = missionService;
        this.tuteurService = tuteurService;
        this.memoireService = memoireService;
    }

    public static class ApprentiFormDto {
        private Integer anneeAcademique;
        private Integer majeure;
        private String nom;
        private String prenom;
        private String mail;
        private String telephone;
        private String remarques;
        private Integer niveau;
        private Boolean archive;
        private Integer entrepriseId;
        private Integer missionId;
        private Integer tuteurId;
        private Integer memoireId;

        public Integer getAnneeAcademique() { return anneeAcademique; }
        public void setAnneeAcademique(Integer anneeAcademique) { this.anneeAcademique = anneeAcademique; }
        public Integer getMajeure() { return majeure; }
        public void setMajeure(Integer majeure) { this.majeure = majeure; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
        public String getTelephone() { return telephone; }
        public void setTelephone(String telephone) { this.telephone = telephone; }
        public String getRemarques() { return remarques; }
        public void setRemarques(String remarques) { this.remarques = remarques; }
        public Integer getNiveau() { return niveau; }
        public void setNiveau(Integer niveau) { this.niveau = niveau; }
        public Boolean getArchive() { return archive; }
        public void setArchive(Boolean archive) { this.archive = archive; }
        public Integer getEntrepriseId() { return entrepriseId; }
        public void setEntrepriseId(Integer entrepriseId) { this.entrepriseId = entrepriseId; }
        public Integer getMissionId() { return missionId; }
        public void setMissionId(Integer missionId) { this.missionId = missionId; }
        public Integer getTuteurId() { return tuteurId; }
        public void setTuteurId(Integer tuteurId) { this.tuteurId = tuteurId; }
        public Integer getMemoireId() { return memoireId; }
        public void setMemoireId(Integer memoireId) { this.memoireId = memoireId; }
    }

    private static ApprentiFormDto fromEntity(Apprenti a) {
        ApprentiFormDto dto = new ApprentiFormDto();
        dto.setAnneeAcademique(a.getAnneeAcademique());
        dto.setMajeure(a.getMajeure());
        dto.setNom(a.getNom());
        dto.setPrenom(a.getPrenom());
        dto.setMail(a.getMail());
        dto.setTelephone(a.getTelephone());
        dto.setRemarques(a.getRemarques());
        dto.setNiveau(a.getNiveau());
        dto.setArchive(a.getArchive());
        if (a.getEntreprise() != null) dto.setEntrepriseId(a.getEntreprise().getId());
        if (a.getMission() != null) dto.setMissionId(a.getMission().getId());
        if (a.getTuteur() != null) dto.setTuteurId(a.getTuteur().getId());
        if (a.getMemoire() != null) dto.setMemoireId(a.getMemoire().getId());
        return dto;
    }

    private Apprenti toApprentiEntity(ApprentiFormDto dto) {
        Apprenti a = new Apprenti();
        a.setAnneeAcademique(dto.getAnneeAcademique());
        a.setMajeure(dto.getMajeure());
        a.setNom(dto.getNom());
        a.setPrenom(dto.getPrenom());
        a.setMail(dto.getMail());
        a.setTelephone(dto.getTelephone());
        a.setRemarques(dto.getRemarques());
        a.setNiveau(dto.getNiveau());
        a.setArchive(dto.getArchive() != null ? dto.getArchive() : false);
        if (dto.getEntrepriseId() != null) {
            entrepriseService.getEntrepriseById(dto.getEntrepriseId()).ifPresent(a::setEntreprise);
        }
        if (dto.getMissionId() != null) {
            missionService.getMissionById(dto.getMissionId()).ifPresent(a::setMission);
        }
        if (dto.getTuteurId() != null) {
            tuteurService.getTuteurById(dto.getTuteurId()).ifPresent(a::setTuteur);
        }
        if (dto.getMemoireId() != null) {
            memoireService.getMemoireById(dto.getMemoireId()).ifPresent(a::setMemoire);
        }
        return a;
    }

    @GetMapping
    public String showListApprentis(Model model) {
        List<Apprenti> listApprentis = apprentiService.getAllApprentiActifs();
        model.addAttribute("listApprentis", listApprentis);
        model.addAttribute("success", "");
        return "apprenti/list";
    }

    @GetMapping("/{id}")
    public String showDetailsApprenti(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.getApprentiById(id);
        model.addAttribute(apprentiStr, apprenti.orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas")));
        return "apprenti/detail";
    }

    @GetMapping("/new")
    public String addNewApprenti(Model model) {
        model.addAttribute(apprentiStr, new ApprentiFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "/apprentis/update");
        return "apprenti/form";
    }

    @PostMapping("/update")
    public String createApprenti(@ModelAttribute ApprentiFormDto dto) {
        Apprenti saved = apprentiService.addApprenti(toApprentiEntity(dto));
        return "redirect:/apprentis/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteApprenti(@PathVariable Integer id) {
        apprentiService.deleteApprenti(id);
        return "redirect:/apprentis";
    }

    @GetMapping("/{id}/edit")
    public String editApprenti(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.getApprentiById(id);
        model.addAttribute(apprentiStr, apprenti.map(ApprentiUiController::fromEntity).orElse(null));
        model.addAttribute("id", apprenti.isPresent() ? id : null);
        model.addAttribute("formAction", apprenti.isPresent() ? "/apprentis/update/" + id : "/apprentis/update");

        model.addAttribute("entreprises", entrepriseService.getAllEntreprise());
        model.addAttribute("tuteurs", tuteurService.getAllTuteur());
        model.addAttribute("memoires", memoireService.getAllMemoire());
        model.addAttribute("missions", missionService.getAllMission());

        return "apprenti/form";
    }

    @PostMapping("/update/{id}")
    public String updateApprenti(@PathVariable Integer id, @ModelAttribute ApprentiFormDto dto, Model model) {
        boolean success = apprentiService.updateApprenti(id, toApprentiEntity(dto));
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
                    list = apprentiService.FilterApprentiByMission(missions.stream().map(Mission::getId).toList());
                }
                break;
            case "annee":
                try {
                    int annee = Integer.parseInt(valeur);
                    list = apprentiService.FilterApprentiByAnneeAcademique(annee);
                } catch (NumberFormatException e) {
                    // Valeur d'année non numérique : aucune recherche effectuée pour ce critère
                }
                break;
            default:
                break;
        }

        model.addAttribute("listApprentis", list);
        return "apprenti/list";
    }
}
