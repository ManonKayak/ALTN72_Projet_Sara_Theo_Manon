package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
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
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(apprentiService.getAllApprentiActifs()).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.showListApprentis(model);
        assertEquals("apprenti/list", view);
        assertNotNull(model.asMap().get("listApprentis"));
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
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewApprenti(model);
        assertEquals("apprenti/form", view);
        assertNotNull(model.asMap().get("apprenti"));
    }

    @Test
    void create_redirectsToDetail() {
        when(apprentiService.addApprenti(any())).thenReturn(apprenti);
        String view = controller.createApprenti(new ApprentiUiController.ApprentiFormDto());
        assertEquals("redirect:/apprentis/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(apprentiService.deleteApprenti(1)).thenReturn(apprenti);
        String view = controller.deleteApprenti(1);
        assertEquals("redirect:/apprentis", view);
    }

    @Test
    void edit_returnsFormViewWithDependencies() {
        when(apprentiService.getApprentiById(1)).thenReturn(Optional.of(apprenti));
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of());
        when(tuteurService.getAllTuteur()).thenReturn(List.of());
        when(memoireService.getAllMemoire()).thenReturn(List.of());
        when(missionService.getAllMission()).thenReturn(List.of());
        Model model = new ExtendedModelMap();
        String view = controller.editApprenti(1, model);
        assertEquals("apprenti/form", view);
        assertTrue(model.asMap().containsKey("entreprises"));
        assertTrue(model.asMap().containsKey("tuteurs"));
    }

    @Test
    void update_redirectsToList() {
        when(apprentiService.updateApprenti(eq(1), any())).thenReturn(true);
        Model model = new ExtendedModelMap();
        String view = controller.updateApprenti(1, new ApprentiUiController.ApprentiFormDto(), model);
        assertEquals("redirect:/apprentis", view);
    }

    @Test
    void filterByNom_returnsListView() {
        when(apprentiService.FilterApprentiByNom("Martin")).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("nom", "Martin", model);
        assertEquals("apprenti/list", view);
        List<?> list = (List<?>) model.asMap().get("listApprentis");
        assertEquals(1, list.size());
    }

    @Test
    void filterByEntreprise_returnsListView() {
        Entreprise e = new Entreprise();
        e.setId(1);
        e.setRaisonSociale("ACME");
        when(entrepriseService.getEntrepriseByName("ACME")).thenReturn(e);
        when(apprentiService.FilterApprentiByEntreprise_Id(1)).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("entreprise", "ACME", model);
        assertEquals("apprenti/list", view);
    }

    @Test
    void filterByMission_returnsListView() {
        Mission m = new Mission();
        m.setId(1);
        m.setMotsCles("Java");
        when(missionService.getMissionByMotsCles("Java")).thenReturn(List.of(m));
        when(apprentiService.FilterApprentiByMission(List.of(1))).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("mission", "Java", model);
        assertEquals("apprenti/list", view);
    }

    @Test
    void filterByAnnee_returnsListView() {
        when(apprentiService.FilterApprentiByAnneeAcademique(2024)).thenReturn(List.of(apprenti));
        Model model = new ExtendedModelMap();
        String view = controller.filterApprenti("annee", "2024", model);
        assertEquals("apprenti/list", view);
    }
}
