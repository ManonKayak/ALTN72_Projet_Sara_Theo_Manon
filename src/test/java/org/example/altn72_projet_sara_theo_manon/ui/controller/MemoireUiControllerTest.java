package org.example.altn72_projet_sara_theo_manon.ui.controller;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.ui.service.MemoireService;
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
class MemoireUiControllerTest {

    @Mock
    private MemoireService memoireService;

    @InjectMocks
    private MemoireUiController controller;

    private Memoire memoire;

    @BeforeEach
    void setUp() {
        memoire = new Memoire();
        memoire.setId(1);
        memoire.setSujet("Architecture microservices");
        memoire.setNote(15.5f);
    }

    @Test
    void showList_returnsListViewWithAttribute() {
        when(memoireService.getAllMemoire()).thenReturn(List.of(memoire));
        Model model = new ExtendedModelMap();
        String view = controller.showListMemoires(model);
        assertEquals("memoire/list", view);
        assertNotNull(model.asMap().get("listMemoires"));
    }

    @Test
    void showDetails_returnsDetailView() {
        when(memoireService.getMemoireById(1)).thenReturn(Optional.of(memoire));
        Model model = new ExtendedModelMap();
        String view = controller.showDetailsMemoire(1, model);
        assertEquals("memoire/detail", view);
        assertEquals(memoire, model.asMap().get("memoire"));
    }

    @Test
    void showDetails_throwsWhenNotFound() {
        when(memoireService.getMemoireById(99)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();
        assertThrows(IllegalStateException.class, () -> controller.showDetailsMemoire(99, model));
    }

    @Test
    void addNew_returnsFormView() {
        Model model = new ExtendedModelMap();
        String view = controller.addNewMemoire(model);
        assertEquals("memoire/form", view);
        assertNotNull(model.asMap().get("memoire"));
    }

    @Test
    void create_redirectsToDetail() {
        when(memoireService.addMemoire(any())).thenReturn(memoire);
        String view = controller.createMemoire(new MemoireUiController.MemoireFormDto());
        assertEquals("redirect:/memoires/1", view);
    }

    @Test
    void delete_redirectsToList() {
        when(memoireService.deleteMemoire(1)).thenReturn(memoire);
        String view = controller.deleteMemoire(1);
        assertEquals("redirect:/memoires", view);
    }

    @Test
    void edit_returnsFormView() {
        when(memoireService.getMemoireById(1)).thenReturn(Optional.of(memoire));
        Model model = new ExtendedModelMap();
        String view = controller.editMemoire(1, model);
        assertEquals("memoire/form", view);
    }

    @Test
    void update_redirectsToDetail() {
        when(memoireService.updateMemoire(eq(1), any())).thenReturn(memoire);
        String view = controller.updateMemoire(1, new MemoireUiController.MemoireFormDto());
        assertEquals("redirect:/memoires/1", view);
    }
}
