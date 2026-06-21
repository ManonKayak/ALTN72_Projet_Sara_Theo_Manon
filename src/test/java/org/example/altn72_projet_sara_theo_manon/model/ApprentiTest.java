package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApprentiTest {

    private Apprenti buildApprenti() {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(1);

        Mission mission = new Mission();
        mission.setId(1);

        Tuteur tuteur = new Tuteur();
        tuteur.setId(1);

        Memoire memoire = new Memoire();
        memoire.setId(1);

        Apprenti a = new Apprenti();
        a.setId(10);
        a.setAnneeAcademique(2024);
        a.setMajeure(1);
        a.setNom("Dupont");
        a.setPrenom("Alice");
        a.setMail("alice@efrei.fr");
        a.setTelephone("0601020304");
        a.setRemarques("Aucune");
        a.setNiveau(3);
        a.setArchive(false);
        a.setEntreprise(entreprise);
        a.setMission(mission);
        a.setTuteur(tuteur);
        a.setMemoire(memoire);
        return a;
    }

    @Test
    void testGettersAndSetters() {
        Apprenti a = buildApprenti();

        assertEquals(10, a.getId());
        assertEquals(2024, a.getAnneeAcademique());
        assertEquals(1, a.getMajeure());
        assertEquals("Dupont", a.getNom());
        assertEquals("Alice", a.getPrenom());
        assertEquals("alice@efrei.fr", a.getMail());
        assertEquals("0601020304", a.getTelephone());
        assertEquals("Aucune", a.getRemarques());
        assertEquals(3, a.getNiveau());
        assertFalse(a.getArchive());
        assertNotNull(a.getEntreprise());
        assertNotNull(a.getMission());
        assertNotNull(a.getTuteur());
        assertNotNull(a.getMemoire());
    }

    @Test
    void testArchiveDefaultFalse() {
        Apprenti a = new Apprenti();
        assertFalse(a.getArchive());
    }

    @Test
    void testMemoireCanBeNull() {
        Apprenti a = buildApprenti();
        a.setMemoire(null);
        assertNull(a.getMemoire());
    }

    @Test
    void testArchiveCanBeTrue() {
        Apprenti a = new Apprenti();
        a.setArchive(true);
        assertTrue(a.getArchive());
    }
}
