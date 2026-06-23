package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.ui.service.TuteurService;
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
class TuteurUiControllerTest {

    @Mock
    private TuteurService tuteurService;

    @InjectMocks
    private TuteurUiController controller;

    private Tuteur tuteur;

    @BeforeEach
    void setUp() {
        tuteur = new Tuteur();
        tuteur.setId(1);
        tuteur.setNom("Dupont");
        tuteur.setPrenom("Jean");
        tuteur.setEmail("jean@corp.fr");
        tuteur.setTelephone("0600000000");
        tuteur.setPoste("CTO");
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(tuteurService.getAllTuteur()).thenReturn(List.of(tuteur));
        Model model = new ExtendedModelMap();
        String view = controller.showListTuteurs(model);
        assertEquals("tuteur/list", view);
        assertNotNull(model.asMap().get("listTuteurs"));
    }

    @Test
    void showDetails_returnsDetailView() {
        when(tuteurService.getTuteurById(1)).thenReturn(Optional.of(tuteur));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsTuteur(1, model);
        assertEquals("tuteur/detail", view);
        assertEquals(tuteur, model.asMap().get("tuteur"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(tuteurService.getTuteurById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsTuteur(99, model));
    }

    @Test
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewTuteur(model);
        assertEquals("tuteur/form", view);
        assertNotNull(model.asMap().get("tuteur"));
    }

    @Test
    void create_redirectsToDetail() {
        when(tuteurService.addTuteur(any())).thenReturn(tuteur);
        String view = controller.createTuteur(new TuteurUiController.TuteurFormDto());
        assertEquals("redirect:/tuteurs/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(tuteurService.deleteTuteur(1)).thenReturn(tuteur);
        String view = controller.deleteTuteur(1);
        assertEquals("redirect:/tuteurs", view);
    }

    @Test
    void edit_returnsFormView() {
        when(tuteurService.getTuteurById(1)).thenReturn(Optional.of(tuteur));
        Model model = new ExtendedModelMap();
        String view = controller.editTuteur(1, model);
        assertEquals("tuteur/form", view);
    }

    @Test
    void update_redirectsToDetail() {
        when(tuteurService.updateTuteur(eq(1), any())).thenReturn(tuteur);
        String view = controller.updateTuteur(1, new TuteurUiController.TuteurFormDto());
        assertEquals("redirect:/tuteurs/1", view);
    }
}
