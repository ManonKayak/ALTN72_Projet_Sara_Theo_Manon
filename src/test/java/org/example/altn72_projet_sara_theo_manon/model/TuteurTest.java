package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TuteurTest {

    @Test
    void testGettersAndSetters() {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(5);

        Tuteur t = new Tuteur();
        t.setId(1);
        t.setNom("Martin");
        t.setPrenom("Jean");
        t.setEmail("jean.martin@corp.fr");
        t.setTelephone("0612345678");
        t.setPoste("Directeur technique");
        t.setRemarques("Très disponible");
        t.setEntreprise(entreprise);

        assertEquals(1, t.getId());
        assertEquals("Martin", t.getNom());
        assertEquals("Jean", t.getPrenom());
        assertEquals("jean.martin@corp.fr", t.getEmail());
        assertEquals("0612345678", t.getTelephone());
        assertEquals("Directeur technique", t.getPoste());
        assertEquals("Très disponible", t.getRemarques());
        assertEquals(5, t.getEntreprise().getId());
    }

    @Test
    void testEntrepriseCanBeNull() {
        Tuteur t = new Tuteur();
        t.setEntreprise(null);
        assertNull(t.getEntreprise());
    }
}
