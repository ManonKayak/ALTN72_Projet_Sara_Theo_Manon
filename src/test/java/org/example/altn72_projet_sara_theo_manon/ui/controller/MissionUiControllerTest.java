package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.ui.service.MissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionUiControllerTest {

    @Mock
    private MissionService missionService;

    @InjectMocks
    private MissionUiController controller;

    private Mission mission;

    @BeforeEach
    void setUp() {
        mission = new Mission();
        mission.setId(1);
        mission.setMotsCles("Java");
        mission.setMetierCible("Dev");
        mission.setCommentaires("OK");
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(missionService.getAllMission()).thenReturn(List.of(mission));
        Model model = new ExtendedModelMap();
        String view = controller.showListMissions(model);
        assertEquals("mission/list", view);
        assertNotNull(model.asMap().get("listMissions"));
    }

    @Test
    void showDetails_returnsDetailView() {
        when(missionService.getMissionById(1)).thenReturn(Optional.of(mission));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsMission(1, model);
        assertEquals("mission/detail", view);
        assertEquals(mission, model.asMap().get("mission"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(missionService.getMissionById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsMission(99, model));
    }

    @Test
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewMission(model);
        assertEquals("mission/form", view);
        assertNotNull(model.asMap().get("mission"));
    }

    @Test
    void create_redirectsToDetail() {
        when(missionService.addMission(any())).thenReturn(mission);
        String view = controller.createMission(mission);
        assertEquals("redirect:/missions/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(missionService.deleteMission(1)).thenReturn(mission);
        String view = controller.deleteMission(1);
        assertEquals("redirect:/missions", view);
    }

    @Test
    void edit_returnsFormView() {
        when(missionService.getMissionById(1)).thenReturn(Optional.of(mission));
        Model model = new ExtendedModelMap();
        String view = controller.editMission(1, model);
        assertEquals("mission/form", view);
    }

    @Test
    void update_redirectsToDetail() {
        when(missionService.updateMission(eq(1), any())).thenReturn(mission);
        String view = controller.updateMission(1, mission);
        assertEquals("redirect:/missions/1", view);
    }
}
