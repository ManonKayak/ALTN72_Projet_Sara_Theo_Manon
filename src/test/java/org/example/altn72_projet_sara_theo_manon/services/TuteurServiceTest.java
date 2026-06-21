package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.TuteurService;
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
class TuteurServiceTest {

    @Mock
    private TuteurRepository tuteurRepository;

    @InjectMocks
    private TuteurService tuteurService;

    private Tuteur tuteur;

    @BeforeEach
    void setUp() {
        tuteur = new Tuteur();
        tuteur.setId(1);
        tuteur.setNom("Dupont");
        tuteur.setPrenom("Jean");
        tuteur.setEmail("jean.dupont@corp.fr");
        tuteur.setTelephone("0600000000");
        tuteur.setPoste("CTO");
    }

    @Test
    void getAllTuteur_returnsAll() {
        when(tuteurRepository.findAll()).thenReturn(List.of(tuteur));
        assertEquals(1, tuteurService.getAllTuteur().size());
        verify(tuteurRepository).findAll();
    }

    @Test
    void getTuteurById_returnsPresent() {
        when(tuteurRepository.findById(1)).thenReturn(Optional.of(tuteur));
        assertTrue(tuteurService.getTuteurById(1).isPresent());
        assertEquals("Dupont", tuteurService.getTuteurById(1).get().getNom());
    }

    @Test
    void getTuteurById_returnsEmptyWhenMissing() {
        when(tuteurRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(tuteurService.getTuteurById(99).isPresent());
    }

    @Test
    void addTuteur_savesAndReturns() {
        when(tuteurRepository.save(tuteur)).thenReturn(tuteur);
        assertEquals(tuteur, tuteurService.addTuteur(tuteur));
        verify(tuteurRepository).save(tuteur);
    }

    @Test
    void deleteTuteur_deletesWhenFound() {
        when(tuteurRepository.findById(1)).thenReturn(Optional.of(tuteur));
        Tuteur deleted = tuteurService.deleteTuteur(1);
        assertEquals(tuteur, deleted);
        verify(tuteurRepository).delete(tuteur);
    }

    @Test
    void deleteTuteur_throwsWhenNotFound() {
        when(tuteurRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> tuteurService.deleteTuteur(99));
    }

    @Test
    void updateTuteur_updatesWhenFound() {
        Tuteur updated = new Tuteur();
        updated.setNom("Martin");
        updated.setPoste("DRH");
        when(tuteurRepository.findById(1)).thenReturn(Optional.of(tuteur));
        when(tuteurRepository.save(any())).thenReturn(tuteur);
        Tuteur result = tuteurService.updateTuteur(1, updated);
        assertNotNull(result);
        verify(tuteurRepository).save(tuteur);
    }

    @Test
    void updateTuteur_throwsWhenNotFound() {
        when(tuteurRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> tuteurService.updateTuteur(99, tuteur));
    }
}
