package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.*;
import org.example.altn72_projet_sara_theo_manon.ui.service.*;
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
class ApprentiUiControllerTest {

    @Mock private ApprentiService apprentiService;
    @Mock private EntrepriseService entrepriseService;
    @Mock private MissionService missionService;
    @Mock private TuteurService tuteurService;
    @Mock private MemoireService memoireService;

    @InjectMocks
    private ApprentiUiController controller;

    private Apprenti apprenti;

    @BeforeEach
    void setUp() {
        apprenti = new Apprenti();
        apprenti.setId(1);
        apprenti.setNom("Martin");
        apprenti.setPrenom("Alice");
        apprenti.setMail("alice@efrei.fr");
        apprenti.setTelephone("0600000000");
        apprenti.setNiveau(3);
        apprenti.setAnneeAcademique(2024);
        apprenti.setMajeure(1);
        apprenti.setArchive(false);
        apprenti.setRemarques("RAS");
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(apprentiService.getAllApprentiActifs()).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.showListApprentis(model);
        assertEquals("apprenti/list", view);
        assertNotNull(model.asMap().get("listApprentis"));
        assertEquals("", model.asMap().get("success"));
    }

    @Test
    void showDetails_returnsDetailView() {
        when(apprentiService.getApprentiById(1)).thenReturn(Optional.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsApprenti(1, model);
        assertEquals("apprenti/detail", view);
        assertEquals(apprenti, model.asMap().get("apprenti"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(apprentiService.getApprentiById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsApprenti(99, model));
    }

    @Test
    void addNew_returnsFormViewWithEmptyDto() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewApprenti(model);
        assertEquals("apprenti/form", view);
        assertNotNull(model.asMap().get("apprenti"));
        assertNull(model.asMap().get("id"));
        assertEquals("/apprentis/update", model.asMap().get("formAction"));
    }

    @Test
    void create_withNullIds_redirectsToDetail() {
        when(apprentiService.addApprenti(any())).thenReturn(apprenti);
        String view = controller.createApprenti(new ApprentiUiController.ApprentiFormDto());
        assertEquals("redirect:/apprentis/1", view);
    }

    @Test
    void create_withAllRelationIds_loadsEachEntity() {
        Entreprise entreprise = new Entreprise(); entreprise.setId(2);
        Mission mission = new Mission(); mission.setId(3);
        Tuteur tuteur = new Tuteur(); tuteur.setId(4);
        Memoire memoire = new Memoire(); memoire.setId(5);

        when(entrepriseService.getEntrepriseById(2)).thenReturn(Optional.of(entreprise));
        when(missionService.getMissionById(3)).thenReturn(Optional.of(mission));
        when(tuteurService.getTuteurById(4)).thenReturn(Optional.of(tuteur));
        when(memoireService.getMemoireById(5)).thenReturn(Optional.of(memoire));
        when(apprentiService.addApprenti(any())).thenReturn(apprenti);

        ApprentiUiController.ApprentiFormDto dto = new ApprentiUiController.ApprentiFormDto();
        dto.setEntrepriseId(2);
        dto.setMissionId(3);
        dto.setTuteurId(4);
        dto.setMemoireId(5);
        dto.setArchive(true);

        String view = controller.createApprenti(dto);
        assertEquals("redirect:/apprentis/1", view);
        verify(entrepriseService).getEntrepriseById(2);
        verify(missionService).getMissionById(3);
        verify(tuteurService).getTuteurById(4);
        verify(memoireService).getMemoireById(5);
    }

    @Test
    void create_withNullArchive_defaultsToFalse() {
        when(apprentiService.addApprenti(any())).thenAnswer(inv -> {
            Apprenti a = inv.getArgument(0);
            assertFalse(a.getArchive());
            a.setId(1);
            return a;
        });
        ApprentiUiController.ApprentiFormDto dto = new ApprentiUiController.ApprentiFormDto();
        dto.setArchive(null);
        controller.createApprenti(dto);
    }

   @Test
    void delete_redirectsToList() {
        when(apprentiService.deleteApprenti(1)).thenReturn(apprenti);
        String view = controller.deleteApprenti(1);
        assertEquals("redirect:/apprentis", view);
    }

    @Test
    void edit_whenFound_populatesModelWithDto() {
        when(apprentiService.getApprentiById(1)).thenReturn(Optional.of(apprenti));
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of());
        when(tuteurService.getAllTuteur()).thenReturn(List.of());
        when(memoireService.getAllMemoire()).thenReturn(List.of());
        when(missionService.getAllMission()).thenReturn(List.of());
        Model model = new ExtendedModelMap();

        String view = controller.editApprenti(1, model);

        assertEquals("apprenti/form", view);
        assertNotNull(model.asMap().get("apprenti"));
        assertEquals(1, model.asMap().get("id"));
        assertEquals("/apprentis/update/1", model.asMap().get("formAction"));
        assertTrue(model.asMap().containsKey("entreprises"));
        assertTrue(model.asMap().containsKey("tuteurs"));
        assertTrue(model.asMap().containsKey("memoires"));
        assertTrue(model.asMap().containsKey("missions"));
    }

    @Test
    void edit_whenFound_withRelations_setsRelationIdsInDto() {
        Entreprise entreprise = new Entreprise(); entreprise.setId(2);
        Mission mission = new Mission(); mission.setId(3);
        Tuteur tuteur = new Tuteur(); tuteur.setId(4);
        Memoire memoire = new Memoire(); memoire.setId(5);
        apprenti.setEntreprise(entreprise);
        apprenti.setMission(mission);
        apprenti.setTuteur(tuteur);
        apprenti.setMemoire(memoire);

        when(apprentiService.getApprentiById(1)).thenReturn(Optional.of(apprenti));
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of());
        when(tuteurService.getAllTuteur()).thenReturn(List.of());
        when(memoireService.getAllMemoire()).thenReturn(List.of());
        when(missionService.getAllMission()).thenReturn(List.of());
        Model model = new ExtendedModelMap();

        controller.editApprenti(1, model);

        ApprentiUiController.ApprentiFormDto dto =
                (ApprentiUiController.ApprentiFormDto) model.asMap().get("apprenti");
        assertEquals(2, dto.getEntrepriseId());
        assertEquals(3, dto.getMissionId());
        assertEquals(4, dto.getTuteurId());
        assertEquals(5, dto.getMemoireId());
    }

    @Test
    void edit_whenNotFound_setsNullApprentiAndCreateFormAction() {
        when(apprentiService.getApprentiById(99)).thenReturn(Optional.empty());
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of());
        when(tuteurService.getAllTuteur()).thenReturn(List.of());
        when(memoireService.getAllMemoire()).thenReturn(List.of());
        when(missionService.getAllMission()).thenReturn(List.of());
        Model model = new ExtendedModelMap();

        String view = controller.editApprenti(99, model);

        assertEquals("apprenti/form", view);
        assertNull(model.asMap().get("apprenti"));
        assertNull(model.asMap().get("id"));
        assertEquals("/apprentis/update", model.asMap().get("formAction"));
    }

    // ── updateApprenti ─────────────────────────────────────────────────────────

    @Test
    void update_whenSuccess_setsSuccessMessage() {
        when(apprentiService.updateApprenti(eq(1), any())).thenReturn(true);
        Model model = new ExtendedModelMap();
        String view = controller.updateApprenti(1, new ApprentiUiController.ApprentiFormDto(), model);
        assertEquals("redirect:/apprentis", view);
        assertEquals("Modification réussie !", model.asMap().get("success"));
    }

    @Test
    void update_whenFails_setsFailureMessage() {
        when(apprentiService.updateApprenti(eq(1), any())).thenReturn(false);
        Model model = new ExtendedModelMap();
        String view = controller.updateApprenti(1, new ApprentiUiController.ApprentiFormDto(), model);
        assertEquals("redirect:/apprentis", view);
        assertEquals("Échec de la modification.", model.asMap().get("success"));
    }

    @Test
    void filterByNom_returnsMatchingApprentis() {
        when(apprentiService.FilterApprentiByNom("Martin")).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("nom", "Martin", model);
        assertEquals("apprenti/list", view);
        assertEquals(1, ((List<?>) model.asMap().get("listApprentis")).size());
    }

    @Test
    void filterByEntreprise_whenFound_returnsApprentis() {
        Entreprise e = new Entreprise(); e.setId(1); e.setRaisonSociale("ACME");
        when(entrepriseService.getEntrepriseByName("ACME")).thenReturn(e);
        when(apprentiService.FilterApprentiByEntreprise_Id(1)).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("entreprise", "ACME", model);
        assertEquals("apprenti/list", view);
        assertEquals(1, ((List<?>) model.asMap().get("listApprentis")).size());
    }

    @Test
    void filterByEntreprise_whenNotFound_returnsEmptyList() {
        when(entrepriseService.getEntrepriseByName("UNKNOWN")).thenReturn(null);
        Model model = new ExtendedModelMap();
        controller.filterApprenti("entreprise", "UNKNOWN", model);
        assertTrue(((List<?>) model.asMap().get("listApprentis")).isEmpty());
    }

    @Test
    void filterByMission_whenMissionsFound_returnsApprentis() {
        Mission m = new Mission(); m.setId(1); m.setMotsCles("Java");
        when(missionService.getMissionByMotsCles("Java")).thenReturn(List.of(m));
        when(apprentiService.FilterApprentiByMission(List.of(1))).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("mission", "Java", model);
        assertEquals("apprenti/list", view);
        assertEquals(1, ((List<?>) model.asMap().get("listApprentis")).size());
    }

    @Test
    void filterByMission_whenNoMissions_returnsEmptyList() {
        when(missionService.getMissionByMotsCles("XYZ")).thenReturn(List.of());
        Model model = new ExtendedModelMap();
        controller.filterApprenti("mission", "XYZ", model);
        assertTrue(((List<?>) model.asMap().get("listApprentis")).isEmpty());
    }

    @Test
    void filterByAnnee_withValidYear_returnsApprentis() {
        when(apprentiService.FilterApprentiByAnneeAcademique(2024)).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("annee", "2024", model);
        assertEquals("apprenti/list", view);
        assertEquals(1, ((List<?>) model.asMap().get("listApprentis")).size());
    }

    @Test
    void filterByAnnee_withInvalidYear_returnsEmptyList() {
        Model model = new ExtendedModelMap();
        controller.filterApprenti("annee", "invalid", model);
        assertTrue(((List<?>) model.asMap().get("listApprentis")).isEmpty());
    }

    @Test
    void filterByUnknownType_returnsEmptyList() {
        Model model = new ExtendedModelMap();
        controller.filterApprenti("unknown", "value", model);
        assertTrue(((List<?>) model.asMap().get("listApprentis")).isEmpty());
    }
}
