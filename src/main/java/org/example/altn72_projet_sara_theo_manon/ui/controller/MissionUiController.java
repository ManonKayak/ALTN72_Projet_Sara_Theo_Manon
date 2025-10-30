package org.example.altn72_projet_sara_theo_manon.ui.controller;

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

    public MissionUiController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    public String showListMissions(Model model) {
        List<Mission> listMissions = missionService.getAllMission();
        model.addAttribute("listMissions", listMissions);
        return "mission/list";
    }

    @GetMapping("/{id}")
    public String showDetailsMission(@PathVariable Integer id,  Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        model.addAttribute("mission", mission.orElseThrow(() -> new IllegalStateException("Cet mission n'existe pas")));
        return "mission/detail";
    }

    @GetMapping("/new")
    public String addNewMission(Model model) {
        model.addAttribute("mission", new Mission());
        model.addAttribute("id", null);
        return "mission/form";
    }

    @PostMapping("/update")
    public String createMission(@RequestBody Mission mission) {
        missionService.addMission(mission);
        return "redirect:/missions/"+mission.getId();
    }

    @PostMapping("{id}/delete")
    public String deleteMission(@PathVariable Integer id) {
        missionService.deleteMission(id);
        return "redirect:/missions/";
    }

    @GetMapping("/{id}/edit")
    public String editMission(@PathVariable Integer id,  Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        model.addAttribute("mission", mission.orElse(null));
        model.addAttribute("id", mission.isPresent() ? id :  null);

        return "mission/form";
    }

    @PostMapping("/update/{id}")
    public String updateMission(@PathVariable Integer id) {
        Optional<Mission> mission = missionService.getMissionById(id);
        if(mission.isPresent()) {
            missionService.updateMission(id, mission.get());
        }
        else{
            return "error/404";
        }

        return "redirect:/missions/"+id;
    }
}
