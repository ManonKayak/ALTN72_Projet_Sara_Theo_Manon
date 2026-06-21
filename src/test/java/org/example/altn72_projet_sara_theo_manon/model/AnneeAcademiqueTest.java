package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnneeAcademiqueTest {

    @Test
    void testGettersAndSetters() {
        AnneeAcademique a = new AnneeAcademique();
        a.setId(1);
        a.setYears("2023-2024");

        assertEquals(1, a.getId());
        assertEquals("2023-2024", a.getYears());
    }

    @Test
    void testDefaultState() {
        AnneeAcademique a = new AnneeAcademique();
        assertNull(a.getId());
        assertNull(a.getYears());
    }
}
