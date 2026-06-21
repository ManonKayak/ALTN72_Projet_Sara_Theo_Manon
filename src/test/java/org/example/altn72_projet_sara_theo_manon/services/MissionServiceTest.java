package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.MissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    private Mission mission;

    @BeforeEach
    void setUp() {
        mission = new Mission();
        mission.setId(1);
        mission.setMotsCles("Java Spring");
        mission.setMetierCible("Développeur Backend");
        mission.setCommentaires("Mission test");
    }

    @Test
    void getAllMission_returnsAll() {
        when(missionRepository.findAll()).thenReturn(List.of(mission));
        List<Mission> result = missionService.getAllMission();
        assertEquals(1, result.size());
        verify(missionRepository).findAll();
    }

    @Test
    void getMissionById_returnsPresent() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        assertTrue(missionService.getMissionById(1).isPresent());
        assertEquals("Java Spring", missionService.getMissionById(1).get().getMotsCles());
    }

    @Test
    void getMissionById_returnsEmptyWhenMissing() {
        when(missionRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(missionService.getMissionById(99).isPresent());
    }

    @Test
    void addMission_savesAndReturns() {
        when(missionRepository.save(mission)).thenReturn(mission);
        assertEquals(mission, missionService.addMission(mission));
        verify(missionRepository).save(mission);
    }

    @Test
    void deleteMission_deletesWhenFound() {
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        Mission deleted = missionService.deleteMission(1);
        assertEquals(mission, deleted);
        verify(missionRepository).delete(mission);
    }

    @Test
    void deleteMission_throwsWhenNotFound() {
        when(missionRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> missionService.deleteMission(99));
    }

    @Test
    void updateMission_updatesWhenFound() {
        Mission updated = new Mission();
        updated.setMotsCles("Kubernetes");
        updated.setMetierCible("DevOps");
        when(missionRepository.findById(1)).thenReturn(Optional.of(mission));
        when(missionRepository.save(any())).thenReturn(mission);
        Mission result = missionService.updateMission(1, updated);
        assertNotNull(result);
        verify(missionRepository).save(mission);
    }

    @Test
    void updateMission_throwsWhenNotFound() {
        when(missionRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> missionService.updateMission(99, mission));
    }

    @Test
    void getMissionByMotsCles_returnsFiltered() {
        when(missionRepository.findByMotsCles("Java Spring")).thenReturn(List.of(mission));
        List<Mission> result = missionService.getMissionByMotsCles("Java Spring");
        assertEquals(1, result.size());
        assertEquals("Java Spring", result.get(0).getMotsCles());
    }
}
