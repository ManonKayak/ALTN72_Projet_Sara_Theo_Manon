package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Niveau;
import org.example.altn72_projet_sara_theo_manon.model.NiveauRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.NiveauService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NiveauServiceTest {

    @Mock
    private NiveauRepository niveauRepository;

    @InjectMocks
    private NiveauService niveauService;

    @Test
    void getNiveaux_returnsAll() {
        Niveau n = new Niveau();
        when(niveauRepository.findAll()).thenReturn(List.of(n));
        List<Niveau> result = niveauService.GetNiveaux();
        assertEquals(1, result.size());
        verify(niveauRepository).findAll();
    }

    @Test
    void getNiveaux_returnsEmptyList() {
        when(niveauRepository.findAll()).thenReturn(List.of());
        assertTrue(niveauService.GetNiveaux().isEmpty());
    }
}
