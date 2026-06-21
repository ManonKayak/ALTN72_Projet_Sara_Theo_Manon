package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NiveauTest {

    @Test
    void testGettersAndSetters() {
        Niveau n = new Niveau();
        n.setId(1);
        n.setName("I1");

        assertEquals(1, n.getId());
        assertEquals("I1", n.getName());
    }

    @Test
    void testDefaultState() {
        Niveau n = new Niveau();
        assertNull(n.getId());
        assertNull(n.getName());
    }
}
