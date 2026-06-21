package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

    @Test
    void testGettersAndSetters() {
        Entreprise e = new Entreprise();
        e.setId(1);
        e.setRaisonSociale("ACME Corp");
        e.setAdresse("42 rue de la Paix");
        e.setInfos("Entreprise de test");

        assertEquals(1, e.getId());
        assertEquals("ACME Corp", e.getRaisonSociale());
        assertEquals("42 rue de la Paix", e.getAdresse());
        assertEquals("Entreprise de test", e.getInfos());
    }

    @Test
    void testInfosCanBeNull() {
        Entreprise e = new Entreprise();
        e.setInfos(null);
        assertNull(e.getInfos());
    }

    @Test
    void testDefaultState() {
        Entreprise e = new Entreprise();
        assertNull(e.getId());
        assertNull(e.getRaisonSociale());
        assertNull(e.getAdresse());
        assertNull(e.getInfos());
    }
}
