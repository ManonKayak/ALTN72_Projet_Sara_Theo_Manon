package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.ApprentiRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.ApprentiService;
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
class ApprentiServiceTest {

    @Mock
    private ApprentiRepository apprentiRepository;

    @InjectMocks
    private ApprentiService apprentiService;

    private Apprenti apprenti;

    @BeforeEach
    void setUp() {
        apprenti = new Apprenti();
        apprenti.setId(1);
        apprenti.setNom("Dupont");
        apprenti.setPrenom("Alice");
        apprenti.setArchive(false);
    }

    @Test
    void getAllApprenti_returnsAll() {
        when(apprentiRepository.findAll()).thenReturn(List.of(apprenti));
        assertEquals(1, apprentiService.getAllApprenti().size());
        verify(apprentiRepository).findAll();
    }

    @Test
    void getAllApprentiActifs_returnsNonArchived() {
        when(apprentiRepository.findByArchiveFalse()).thenReturn(List.of(apprenti));
        List<Apprenti> result = apprentiService.getAllApprentiActifs();
        assertEquals(1, result.size());
        assertFalse(result.get(0).getArchive());
    }

    @Test
    void getApprentiById_returnsPresent() {
        when(apprentiRepository.findById(1)).thenReturn(Optional.of(apprenti));
        assertTrue(apprentiService.getApprentiById(1).isPresent());
    }

    @Test
    void getApprentiById_returnsEmptyWhenMissing() {
        when(apprentiRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(apprentiService.getApprentiById(99).isPresent());
    }

    @Test
    void addApprenti_savesAndReturns() {
        when(apprentiRepository.save(apprenti)).thenReturn(apprenti);
        assertEquals(apprenti, apprentiService.addApprenti(apprenti));
        verify(apprentiRepository).save(apprenti);
    }

    @Test
    void deleteApprenti_deletesWhenFound() {
        when(apprentiRepository.findById(1)).thenReturn(Optional.of(apprenti));
        Apprenti deleted = apprentiService.deleteApprenti(1);
        assertEquals(apprenti, deleted);
        verify(apprentiRepository).delete(apprenti);
    }

    @Test
    void deleteApprenti_throwsWhenNotFound() {
        when(apprentiRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> apprentiService.deleteApprenti(99));
    }

    @Test
    void updateApprenti_returnsTrueWhenFound() {
        when(apprentiRepository.findById(1)).thenReturn(Optional.of(apprenti));
        when(apprentiRepository.save(any())).thenReturn(apprenti);
        assertTrue(apprentiService.updateApprenti(1, apprenti));
    }

    @Test
    void updateApprenti_returnsFalseWhenNotFound() {
        when(apprentiRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(apprentiService.updateApprenti(99, apprenti));
    }

    @Test
    void filterByNom_delegatesToRepository() {
        when(apprentiRepository.findByNom("Dupont")).thenReturn(List.of(apprenti));
        assertEquals(1, apprentiService.FilterApprentiByNom("Dupont").size());
    }

    @Test
    void filterByEntrepriseId_delegatesToRepository() {
        when(apprentiRepository.findByEntreprise_Id(5)).thenReturn(List.of(apprenti));
        assertEquals(1, apprentiService.FilterApprentiByEntreprise_Id(5).size());
    }

    @Test
    void filterByAnneeAcademique_delegatesToRepository() {
        when(apprentiRepository.findByAnneeAcademique(2024)).thenReturn(List.of(apprenti));
        assertEquals(1, apprentiService.FilterApprentiByAnneeAcademique(2024).size());
    }

    @Test
    void filterByMission_delegatesToRepository() {
        when(apprentiRepository.findByMission_IdIn(List.of(1, 2))).thenReturn(List.of(apprenti));
        assertEquals(1, apprentiService.FilterApprentiByMission(List.of(1, 2)).size());
    }

    @Test
    void archiveApprenti_callsUpdateArchive() {
        apprentiService.ArchiveApprenti(1);
        verify(apprentiRepository).updateArchive(1);
    }
}
