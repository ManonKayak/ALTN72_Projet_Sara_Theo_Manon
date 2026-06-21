package org.example.altn72_projet_sara_theo_manon.errors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void notFoundException_storesMessage() {
        NotFoundException ex = new NotFoundException("Ressource introuvable");
        assertEquals("Ressource introuvable", ex.getMessage());
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void badRequestException_storesMessage() {
        BadRequestException ex = new BadRequestException("Requête invalide");
        assertEquals("Requête invalide", ex.getMessage());
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void notFoundException_canBeThrown() {
        assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("not found");
        });
    }

    @Test
    void badRequestException_canBeThrown() {
        assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException("bad request");
        });
    }
}
