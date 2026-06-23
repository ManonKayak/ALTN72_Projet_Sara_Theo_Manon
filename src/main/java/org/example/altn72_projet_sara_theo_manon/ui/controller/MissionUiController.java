package org.example.altn72_projet_sara_theo_manon.ui.controller;

import lombok.Data;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/missions")
public class MissionUiController {

    private final MissionService missionService;
    private final String missionStr = "mission";

    public MissionUiController(MissionService missionService) {
        this.missionService = missionService;
    }

    @Data
    public static class MissionFormDto {
        private String motsCles;
        private String metierCible;
        private String commentaires;
    }

    private static MissionFormDto fromEntity(Mission m) {
        MissionFormDto dto = new MissionFormDto();
        dto.setMotsCles(m.getMotsCles());
        dto.setMetierCible(m.getMetierCible());
        dto.setCommentaires(m.getCommentaires());
        return dto;
    }

    private static Mission toEntity(MissionFormDto dto) {
        Mission m = new Mission();
        m.setMotsCles(dto.getMotsCles());
        m.setMetierCible(dto.getMetierCible());
        m.setCommentaires(dto.getCommentaires());
        return m;
    }

    @GetMapping
    public String showListMissions(Model model) {
        List<Mission> listMissions = missionService.getAllMission();
        model.addAttribute("listMissions", listMissions);
        return "mission/list";
    }

    @GetMapping("/{id}")
    public String showDetailsMission(@PathVariable Integer id, Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        model.addAttribute(missionStr, mission.orElseThrow(() -> new IllegalStateException("Cet mission n'existe pas")));
        return "mission/detail";
    }

    @GetMapping("/new")
    public String addNewMission(Model model) {
        model.addAttribute(missionStr, new MissionFormDto());
        model.addAttribute("id", null);
        model.addAttribute("formAction", "missions/update");
        return "mission/form";
    }

    @PostMapping("/update")
    public String createMission(@ModelAttribute MissionFormDto dto) {
        Mission saved = missionService.addMission(toEntity(dto));
        return "redirect:/missions/" + saved.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteMission(@PathVariable Integer id) {
        missionService.deleteMission(id);
        return "redirect:/missions";
    }

    @GetMapping("/{id}/edit")
    public String editMission(@PathVariable Integer id, Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        model.addAttribute(missionStr, mission.map(MissionUiController::fromEntity).orElse(null));
        model.addAttribute("id", mission.isPresent() ? id : null);
        model.addAttribute("formAction", mission.isPresent() ? "missions/update/" + id : "mission/update");
        return "mission/form";
    }

    @PostMapping("/update/{id}")
    public String updateMission(@PathVariable Integer id, @ModelAttribute MissionFormDto dto) {
        missionService.updateMission(id, toEntity(dto));
        return "redirect:/missions/" + id;
    }
}
