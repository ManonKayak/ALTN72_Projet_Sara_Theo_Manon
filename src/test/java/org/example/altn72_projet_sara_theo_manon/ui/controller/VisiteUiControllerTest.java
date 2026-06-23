package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteService;
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

    // ── showListVisites ────────────────────────────────────────────────────────

    @Test
    void showList_returnsListViewWithAttribute() {
        when(visiteService.getAllVisite()).thenReturn(List.of(visite));
        Model model = new ExtendedModelMap();
        String view = controller.showListVisites(model);
        assertEquals("visite/list", view);
        assertNotNull(model.asMap().get("listVisites"));
    }

    // ── showDetailsVisite ──────────────────────────────────────────────────────

    @Test
    void showDetails_returnsDetailViewWithEntity() {
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

    // ── addNewVisite ───────────────────────────────────────────────────────────

    @Test
    void addNew_returnsFormViewWithEmptyDtoAndNullId() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewVisite(model);
        assertEquals("visite/form", view);
        assertNotNull(model.asMap().get("visite"));
        assertNull(model.asMap().get("id"));
        assertEquals("visites/update", model.asMap().get("formAction"));
    }

    // ── createVisite ───────────────────────────────────────────────────────────

    @Test
    void create_buildsEntityFromDtoAndRedirectsToDetail() {
        when(visiteService.addVisite(any())).thenReturn(visite);
        ArgumentCaptor<Visite> captor = ArgumentCaptor.forClass(Visite.class);

        VisiteUiController.VisiteFormDto dto = new VisiteUiController.VisiteFormDto();
        dto.setDate(20250601);
        dto.setFormat(1);
        dto.setCommentaire("Visite OK");

        String view = controller.createVisite(dto);

        verify(visiteService).addVisite(captor.capture());
        assertEquals(20250601, captor.getValue().getDate());
        assertEquals(1, captor.getValue().getFormat());
        assertEquals("Visite OK", captor.getValue().getCommentaire());
        assertEquals("redirect:/visites/1", view);
    }

    // ── deleteVisite ───────────────────────────────────────────────────────────

    @Test
    void delete_redirectsToList() {
        when(visiteService.deleteVisite(1)).thenReturn(visite);
        String view = controller.deleteVisite(1);
        assertEquals("redirect:/visites", view);
    }

    // ── editVisite ─────────────────────────────────────────────────────────────

    @Test
    void edit_whenFound_populatesDtoFromEntityAndSetsFormAction() {
        when(visiteService.getVisiteById(1)).thenReturn(Optional.of(visite));
        Model model = new ExtendedModelMap();

        String view = controller.editVisite(1, model);

        assertEquals("visite/form", view);
        assertEquals(1, model.asMap().get("id"));
        assertEquals("visites/update/1", model.asMap().get("formAction"));

        VisiteUiController.VisiteFormDto dto =
                (VisiteUiController.VisiteFormDto) model.asMap().get("visite");
        assertNotNull(dto);
        assertEquals(20250601, dto.getDate());
        assertEquals(1, dto.getFormat());
        assertEquals("Visite OK", dto.getCommentaire());
    }

    @Test
    void edit_whenNotFound_setsNullDtoAndCreateFormAction() {
        when(visiteService.getVisiteById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();

        String view = controller.editVisite(99, model);

        assertEquals("visite/form", view);
        assertNull(model.asMap().get("visite"));
        assertNull(model.asMap().get("id"));
        assertEquals("visites/update", model.asMap().get("formAction"));
    }

    // ── updateVisite ───────────────────────────────────────────────────────────

    @Test
    void update_buildsEntityFromDtoAndRedirectsToDetail() {
        when(visiteService.updateVisite(eq(1), any())).thenReturn(visite);
        ArgumentCaptor<Visite> captor = ArgumentCaptor.forClass(Visite.class);

        VisiteUiController.VisiteFormDto dto = new VisiteUiController.VisiteFormDto();
        dto.setDate(20251201);
        dto.setFormat(2);
        dto.setCommentaire("Mise à jour");

        String view = controller.updateVisite(1, dto);

        verify(visiteService).updateVisite(eq(1), captor.capture());
        assertEquals(20251201, captor.getValue().getDate());
        assertEquals(2, captor.getValue().getFormat());
        assertEquals("Mise à jour", captor.getValue().getCommentaire());
        assertEquals("redirect:/visites/1", view);
    }
}
