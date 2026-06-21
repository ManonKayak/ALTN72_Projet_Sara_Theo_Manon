package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisiteTest {

    @Test
    void testGettersAndSetters() {
        Apprenti apprenti = new Apprenti();
        apprenti.setId(1);

        Entreprise entreprise = new Entreprise();
        entreprise.setId(2);

        Visite v = new Visite();
        v.setId(10);
        v.setDate(20240610);
        v.setFormat(1);
        v.setCommentaire("Visite en présentiel");
        v.setApprenti(apprenti);
        v.setEntreprise(entreprise);

        assertEquals(10, v.getId());
        assertEquals(20240610, v.getDate());
        assertEquals(1, v.getFormat());
        assertEquals("Visite en présentiel", v.getCommentaire());
        assertEquals(1, v.getApprenti().getId());
        assertEquals(2, v.getEntreprise().getId());
    }

    @Test
    void testOptionalFieldsCanBeNull() {
        Visite v = new Visite();
        v.setFormat(null);
        v.setCommentaire(null);
        assertNull(v.getFormat());
        assertNull(v.getCommentaire());
    }
}
