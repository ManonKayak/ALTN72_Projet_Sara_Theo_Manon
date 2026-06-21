package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoireTest {

    @Test
    void testGettersAndSetters() {
        Memoire m = new Memoire();
        m.setId(1);
        m.setSujet("IA dans l'industrie");
        m.setNote(16.5f);
        m.setCommentaire("Bon travail");
        m.setDateSoutenance(20240615);
        m.setNoteSoutenance(17.0f);
        m.setCommentaireSoutenance("Excellente présentation");

        assertEquals(1, m.getId());
        assertEquals("IA dans l'industrie", m.getSujet());
        assertEquals(16.5f, m.getNote());
        assertEquals("Bon travail", m.getCommentaire());
        assertEquals(20240615, m.getDateSoutenance());
        assertEquals(17.0f, m.getNoteSoutenance());
        assertEquals("Excellente présentation", m.getCommentaireSoutenance());
    }

    @Test
    void testOptionalFieldsCanBeNull() {
        Memoire m = new Memoire();
        m.setNote(null);
        m.setCommentaire(null);
        m.setDateSoutenance(null);
        m.setNoteSoutenance(null);
        m.setCommentaireSoutenance(null);

        assertNull(m.getNote());
        assertNull(m.getCommentaire());
        assertNull(m.getDateSoutenance());
        assertNull(m.getNoteSoutenance());
        assertNull(m.getCommentaireSoutenance());
    }
}
