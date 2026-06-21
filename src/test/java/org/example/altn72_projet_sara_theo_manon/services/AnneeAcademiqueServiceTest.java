package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.AnneeAcademique;
import org.example.altn72_projet_sara_theo_manon.model.AnneeAcademiqueRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.AnneeAcademiqueService;
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
class AnneeAcademiqueServiceTest {

    @Mock
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    @InjectMocks
    private AnneeAcademiqueService anneeAcademiqueService;

    @Test
    void getAnneeById_returnsPresent() {
        AnneeAcademique annee = new AnneeAcademique();
        when(anneeAcademiqueRepository.findById(1)).thenReturn(Optional.of(annee));
        assertTrue(anneeAcademiqueService.getAnneeById(1).isPresent());
    }

    @Test
    void getAnneeById_returnsEmptyWhenMissing() {
        when(anneeAcademiqueRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(anneeAcademiqueService.getAnneeById(99).isPresent());
    }

    @Test
    void getAllAnnee_returnsAll() {
        AnneeAcademique annee = new AnneeAcademique();
        when(anneeAcademiqueRepository.findAll()).thenReturn(List.of(annee));
        assertEquals(1, anneeAcademiqueService.GetAllAnnee().size());
        verify(anneeAcademiqueRepository).findAll();
    }
}
