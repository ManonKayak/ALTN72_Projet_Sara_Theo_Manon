package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class EntrepriseUiControllerTest {

    @Mock
    private EntrepriseService entrepriseService;

    @InjectMocks
    private EntrepriseUiController controller;

    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise();
        entreprise.setId(1);
        entreprise.setRaisonSociale("ACME Corp");
        entreprise.setAdresse("1 rue Test");
        entreprise.setInfos("Infos ACME");
    }

    // ── showListEntreprises ────────────────────────────────────────────────────

    @Test
    void showList_returnsListViewWithAttribute() {
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of(entreprise));
        Model model = new ExtendedModelMap();
        String view = controller.showListEntreprises(model);
        assertEquals("entreprise/list", view);
        assertNotNull(model.asMap().get("listEntreprises"));
    }

    // ── showDetailsEntreprise ──────────────────────────────────────────────────

    @Test
    void showDetails_returnsDetailViewWithEntity() {
        when(entrepriseService.getEntrepriseById(1)).thenReturn(Optional.of(entreprise));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsEntreprise(1, model);
        assertEquals("entreprise/detail", view);
        assertEquals(entreprise, model.asMap().get("entreprise"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(entrepriseService.getEntrepriseById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsEntreprise(99, model));
    }

    // ── addNewEntreprise ───────────────────────────────────────────────────────

    @Test
    void addNew_returnsFormViewWithEmptyDtoAndNullId() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewEntreprise(model);
        assertEquals("entreprise/form", view);
        assertNotNull(model.asMap().get("entreprise"));
        assertNull(model.asMap().get("id"));
        assertEquals("entreprises/update", model.asMap().get("formAction"));
    }

    // ── createEntreprise ───────────────────────────────────────────────────────

    @Test
    void create_buildsEntityFromDtoAndRedirectsToDetail() {
        when(entrepriseService.addEntreprise(any())).thenReturn(entreprise);
        ArgumentCaptor<Entreprise> captor = ArgumentCaptor.forClass(Entreprise.class);

        EntrepriseUiController.EntrepriseFormDto dto = new EntrepriseUiController.EntrepriseFormDto();
        dto.setRaisonSociale("ACME Corp");
        dto.setAdresse("1 rue Test");
        dto.setInfos("Infos ACME");

        String view = controller.createEntreprise(dto);

        verify(entrepriseService).addEntreprise(captor.capture());
        assertEquals("ACME Corp", captor.getValue().getRaisonSociale());
        assertEquals("1 rue Test", captor.getValue().getAdresse());
        assertEquals("Infos ACME", captor.getValue().getInfos());
        assertEquals("redirect:/entreprises/1", view);
    }

    // ── deleteEntreprise ───────────────────────────────────────────────────────

    @Test
    void delete_redirectsToList() {
        when(entrepriseService.deleteEntreprise(1)).thenReturn(entreprise);
        String view = controller.deleteEntreprise(1);
        assertEquals("redirect:/entreprises", view);
    }

    // ── editEntreprise ─────────────────────────────────────────────────────────

    @Test
    void edit_whenFound_populatesDtoFromEntityAndSetsFormAction() {
        when(entrepriseService.getEntrepriseById(1)).thenReturn(Optional.of(entreprise));
        Model model = new ExtendedModelMap();

        String view = controller.editEntreprise(1, model);

        assertEquals("entreprise/form", view);
        assertEquals(1, model.asMap().get("id"));
        assertEquals("entreprises/update/1", model.asMap().get("formAction"));

        EntrepriseUiController.EntrepriseFormDto dto =
                (EntrepriseUiController.EntrepriseFormDto) model.asMap().get("entreprise");
        assertNotNull(dto);
        assertEquals("ACME Corp", dto.getRaisonSociale());
        assertEquals("1 rue Test", dto.getAdresse());
        assertEquals("Infos ACME", dto.getInfos());
    }

    @Test
    void edit_whenNotFound_setsNullDtoAndCreateFormAction() {
        when(entrepriseService.getEntrepriseById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();

        String view = controller.editEntreprise(99, model);

        assertEquals("entreprise/form", view);
        assertNull(model.asMap().get("entreprise"));
        assertNull(model.asMap().get("id"));
        assertEquals("entreprises/update", model.asMap().get("formAction"));
    }

    // ── updateEntreprise ───────────────────────────────────────────────────────

    @Test
    void update_buildsEntityFromDtoAndRedirectsToDetail() {
        when(entrepriseService.updateEntreprise(eq(1), any())).thenReturn(entreprise);
        ArgumentCaptor<Entreprise> captor = ArgumentCaptor.forClass(Entreprise.class);

        EntrepriseUiController.EntrepriseFormDto dto = new EntrepriseUiController.EntrepriseFormDto();
        dto.setRaisonSociale("New Name");
        dto.setAdresse("New Address");
        dto.setInfos("New Infos");

        String view = controller.updateEntreprise(1, dto);

        verify(entrepriseService).updateEntreprise(eq(1), captor.capture());
        assertEquals("New Name", captor.getValue().getRaisonSociale());
        assertEquals("New Address", captor.getValue().getAdresse());
        assertEquals("redirect:/entreprises/1", view);
    }
}
