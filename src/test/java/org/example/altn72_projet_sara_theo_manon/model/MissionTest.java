package org.example.altn72_projet_sara_theo_manon.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {

    @Test
    void testGettersAndSetters() {
        Mission m = new Mission();
        m.setId(1);
        m.setMotsCles("Java, Spring, DevOps");
        m.setMetierCible("Développeur backend");
        m.setCommentaires("Mission très intéressante");

        assertEquals(1, m.getId());
        assertEquals("Java, Spring, DevOps", m.getMotsCles());
        assertEquals("Développeur backend", m.getMetierCible());
        assertEquals("Mission très intéressante", m.getCommentaires());
    }

    @Test
    void testDefaultState() {
        Mission m = new Mission();
        assertNull(m.getId());
        assertNull(m.getMotsCles());
        assertNull(m.getMetierCible());
        assertNull(m.getCommentaires());
    }
}
