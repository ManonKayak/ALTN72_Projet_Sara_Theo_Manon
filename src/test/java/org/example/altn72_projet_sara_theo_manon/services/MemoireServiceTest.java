package org.example.altn72_projet_sara_theo_manon.services;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
import org.example.altn72_projet_sara_theo_manon.ui.service.MemoireService;
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
class MemoireServiceTest {

    @Mock
    private MemoireRepository memoireRepository;

    @InjectMocks
    private MemoireService memoireService;

    private Memoire memoire;

    @BeforeEach
    void setUp() {
        memoire = new Memoire();
        memoire.setId(1);
        memoire.setSujet("Architecture microservices");
        memoire.setNote(15.5f);
        memoire.setCommentaire("Bon travail");
        memoire.setDateSoutenance(20250615);
        memoire.setNoteSoutenance(16.0f);
    }

    @Test
    void getAllMemoire_returnsAll() {
        when(memoireRepository.findAll()).thenReturn(List.of(memoire));
        assertEquals(1, memoireService.getAllMemoire().size());
        verify(memoireRepository).findAll();
    }

    @Test
    void getMemoireById_returnsPresent() {
        when(memoireRepository.findById(1)).thenReturn(Optional.of(memoire));
        assertTrue(memoireService.getMemoireById(1).isPresent());
        assertEquals("Architecture microservices", memoireService.getMemoireById(1).get().getSujet());
    }

    @Test
    void getMemoireById_returnsEmptyWhenMissing() {
        when(memoireRepository.findById(99)).thenReturn(Optional.empty());
        assertFalse(memoireService.getMemoireById(99).isPresent());
    }

    @Test
    void addMemoire_savesAndReturns() {
        when(memoireRepository.save(memoire)).thenReturn(memoire);
        assertEquals(memoire, memoireService.addMemoire(memoire));
        verify(memoireRepository).save(memoire);
    }

    @Test
    void deleteMemoire_deletesWhenFound() {
        when(memoireRepository.findById(1)).thenReturn(Optional.of(memoire));
        Memoire deleted = memoireService.deleteMemoire(1);
        assertEquals(memoire, deleted);
        verify(memoireRepository).delete(memoire);
    }

    @Test
    void deleteMemoire_throwsWhenNotFound() {
        when(memoireRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> memoireService.deleteMemoire(99));
    }

    @Test
    void updateMemoire_updatesWhenFound() {
        Memoire updated = new Memoire();
        updated.setSujet("Cloud computing");
        when(memoireRepository.findById(1)).thenReturn(Optional.of(memoire));
        when(memoireRepository.save(any())).thenReturn(memoire);
        Memoire result = memoireService.updateMemoire(1, updated);
        assertNotNull(result);
        verify(memoireRepository).save(memoire);
    }

    @Test
    void updateMemoire_throwsWhenNotFound() {
        when(memoireRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> memoireService.updateMemoire(99, memoire));
    }
}
