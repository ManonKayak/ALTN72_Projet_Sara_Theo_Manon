package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseService;
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
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(entrepriseService.getAllEntreprise()).thenReturn(List.of(entreprise));
        Model model = new ExtendedModelMap();
        String view = controller.showListEntreprises(model);
        assertEquals("entreprise/list", view);
        assertNotNull(model.asMap().get("listEntreprises"));
    }

    @Test
    void showDetails_returnsDetailView() {
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

    @Test
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewEntreprise(model);
        assertEquals("entreprise/form", view);
        assertNotNull(model.asMap().get("entreprise"));
    }

    @Test
    void create_redirectsToDetail() {
        when(entrepriseService.addEntreprise(any())).thenReturn(entreprise);
        String view = controller.createEntreprise(new EntrepriseUiController.EntrepriseFormDto());
        assertEquals("redirect:/entreprises/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(entrepriseService.deleteEntreprise(1)).thenReturn(entreprise);
        String view = controller.deleteEntreprise(1);
        assertEquals("redirect:/entreprises", view);
    }

    @Test
    void edit_returnsFormView() {
        when(entrepriseService.getEntrepriseById(1)).thenReturn(Optional.of(entreprise));
        Model model = new ExtendedModelMap();
        String view = controller.editEntreprise(1, model);
        assertEquals("entreprise/form", view);
        assertNotNull(model.asMap().get("entreprise"));
    }

    @Test
    void update_redirectsToDetail() {
        when(entrepriseService.updateEntreprise(eq(1), any())).thenReturn(entreprise);
        String view = controller.updateEntreprise(1, new EntrepriseUiController.EntrepriseFormDto());
        assertEquals("redirect:/entreprises/1", view);
    }
}
