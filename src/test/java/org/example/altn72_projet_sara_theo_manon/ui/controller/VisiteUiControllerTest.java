package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteService;
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
class VisiteUiControllerTest {

    @Mock
    private VisiteService visiteService;

    @InjectMocks
    private VisiteUiController controller;

    private Visite visite;

    @BeforeEach
    void setUp() {
        visite = new Visite();
        visite.setId(1);
        visite.setDate(20250601);
        visite.setFormat(1);
        visite.setCommentaire("Visite OK");
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(visiteService.getAllVisite()).thenReturn(List.of(visite));
        Model model = new ExtendedModelMap();
        String view = controller.showListVisites(model);
        assertEquals("visite/list", view);
        assertNotNull(model.asMap().get("listVisites"));
    }

    @Test
    void showDetails_returnsDetailView() {
        when(visiteService.getVisiteById(1)).thenReturn(Optional.of(visite));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsVisite(1, model);
        assertEquals("visite/detail", view);
        assertEquals(visite, model.asMap().get("visite"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(visiteService.getVisiteById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsVisite(99, model));
    }

    @Test
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewVisite(model);
        assertEquals("visite/form", view);
        assertNotNull(model.asMap().get("visite"));
    }

    @Test
    void create_redirectsToDetail() {
        when(visiteService.addVisite(any())).thenReturn(visite);
        String view = controller.createVisite(new VisiteUiController.VisiteFormDto());
        assertEquals("redirect:/visites/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(visiteService.deleteVisite(1)).thenReturn(visite);
        String view = controller.deleteVisite(1);
        assertEquals("redirect:/visites", view);
    }

    @Test
    void edit_returnsFormView() {
        when(visiteService.getVisiteById(1)).thenReturn(Optional.of(visite));
        Model model = new ExtendedModelMap();
        String view = controller.editVisite(1, model);
        assertEquals("visite/form", view);
    }

    @Test
    void update_redirectsToDetail() {
        when(visiteService.updateVisite(eq(1), any())).thenReturn(visite);
        String view = controller.updateVisite(1, new VisiteUiController.VisiteFormDto());
        assertEquals("redirect:/visites/1", view);
    }
}
