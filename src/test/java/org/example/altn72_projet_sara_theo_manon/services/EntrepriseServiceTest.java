package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseService;
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
class EntrepriseServiceTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseService entrepriseService;

    private Entreprise entreprise;

    @BeforeEach
    void setUp() {
        entreprise = new Entreprise();
        entreprise.setId(1);
        entreprise.setRaisonSociale("ACME Corp");
        entreprise.setAdresse("1 rue Test");
    }

    @Test
    void getAllEntreprise_returnsAll() {
        when(entrepriseRepository.findAll()).thenReturn(List.of(entreprise));
        assertEquals(1, entrepriseService.getAllEntreprise().size());
        verify(entrepriseRepository).findAll();
    }

    @Test
    void getEntrepriseById_returnsPresent() {
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));
        assertTrue(entrepriseService.getEntrepriseById(1).isPresent());
        assertEquals("ACME Corp", entrepriseService.getEntrepriseById(1).get().getRaisonSociale());
    }

    @Test
    void getEntrepriseById_returnsEmptyWhenMissing() {
        when(entrepriseRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(entrepriseService.getEntrepriseById(99).isPresent());
    }

    @Test
    void addEntreprise_savesAndReturns() {
        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);
        assertEquals(entreprise, entrepriseService.addEntreprise(entreprise));
        verify(entrepriseRepository).save(entreprise);
    }

    @Test
    void deleteEntreprise_deletesWhenFound() {
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));
        Entreprise deleted = entrepriseService.deleteEntreprise(1);
        assertEquals(entreprise, deleted);
        verify(entrepriseRepository).delete(entreprise);
    }

    @Test
    void deleteEntreprise_throwsWhenNotFound() {
        when(entrepriseRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> entrepriseService.deleteEntreprise(99));
    }

    @Test
    void updateEntreprise_updatesWhenFound() {
        Entreprise updated = new Entreprise();
        updated.setRaisonSociale("NewCorp");
        updated.setAdresse("New Adresse");
        when(entrepriseRepository.findById(1)).thenReturn(Optional.of(entreprise));
        when(entrepriseRepository.save(any())).thenReturn(entreprise);
        Entreprise result = entrepriseService.updateEntreprise(1, updated);
        assertNotNull(result);
        verify(entrepriseRepository).save(entreprise);
    }

    @Test
    void updateEntreprise_throwsWhenNotFound() {
        when(entrepriseRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> entrepriseService.updateEntreprise(99, entreprise));
    }

    @Test
    void getEntrepriseByName_returnsFromRepository() {
        when(entrepriseRepository.findByRaisonSociale("ACME Corp")).thenReturn(entreprise);
        Entreprise result = entrepriseService.getEntrepriseByName("ACME Corp");
        assertEquals("ACME Corp", result.getRaisonSociale());
    }
}
