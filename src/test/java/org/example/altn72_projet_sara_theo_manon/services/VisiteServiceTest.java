package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.model.VisiteRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteService;
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
class VisiteServiceTest {

    @Mock
    private VisiteRepository visiteRepository;

    @InjectMocks
    private VisiteService visiteService;

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
    void getAllVisite_returnsAll() {
        when(visiteRepository.findAll()).thenReturn(List.of(visite));
        assertEquals(1, visiteService.getAllVisite().size());
        verify(visiteRepository).findAll();
    }

    @Test
    void getVisiteById_returnsPresent() {
        when(visiteRepository.findById(1)).thenReturn(Optional.of(visite));
        assertTrue(visiteService.getVisiteById(1).isPresent());
        assertEquals("Visite OK", visiteService.getVisiteById(1).get().getCommentaire());
    }

    @Test
    void getVisiteById_returnsEmptyWhenMissing() {
        when(visiteRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(visiteService.getVisiteById(99).isPresent());
    }

    @Test
    void addVisite_savesAndReturns() {
        when(visiteRepository.save(visite)).thenReturn(visite);
        assertEquals(visite, visiteService.addVisite(visite));
        verify(visiteRepository).save(visite);
    }

    @Test
    void deleteVisite_deletesWhenFound() {
        when(visiteRepository.findById(1)).thenReturn(Optional.of(visite));
        Visite deleted = visiteService.deleteVisite(1);
        assertEquals(visite, deleted);
        verify(visiteRepository).delete(visite);
    }

    @Test
    void deleteVisite_throwsWhenNotFound() {
        when(visiteRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> visiteService.deleteVisite(99));
    }

    @Test
    void updateVisite_updatesWhenFound() {
        Visite updated = new Visite();
        updated.setCommentaire("Mise à jour");
        when(visiteRepository.findById(1)).thenReturn(Optional.of(visite));
        when(visiteRepository.save(any())).thenReturn(visite);
        Visite result = visiteService.updateVisite(1, updated);
        assertNotNull(result);
        verify(visiteRepository).save(visite);
    }

    @Test
    void updateVisite_throwsWhenNotFound() {
        when(visiteRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> visiteService.updateVisite(99, visite));
    }
}
