package com.itacademy.cliagenda.note.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestNotes {

    @Test
    void testGetId() {
        note nota = new note(1, "Cuerpo de prueba");
        assertEquals(1, nota.getId());
    }

    @Test
    void testGetBody() {
        note nota = new note(1, "Cuerpo de prueba");
        assertEquals("Cuerpo de prueba", nota.getBody());
    }

    @Test
    void testGetCreationDate() {
        note nota = new note(1, "Cuerpo de prueba");
        assertNotNull(nota.getCreationDate());
    }

    @Test
    void testChangeBodyValido() {
        note nota = new note(1, "Cuerpo original");
        nota.changeBody("Cuerpo actualizado");
        assertEquals("Cuerpo actualizado", nota.getBody());
    }

    @Test
    void testChangeBodyExcedeLongitud() {
        note nota = new note(1, "Cuerpo original");
        String bodyLargo = "a".repeat(251);
        nota.changeBody(bodyLargo);
        assertEquals("Cuerpo original", nota.getBody());
    }

    @Test
    void testConstructorBodyValido() {
        note nota = new note(1, "Cuerpo válido");
        assertEquals("Cuerpo válido", nota.getBody());
    }

    @Test
    void testConstructorBodyNulo() {
        note nota = new note(1, null);
        assertNull(nota.getBody());
    }

    @Test
    void testConstructorBodyExcedeLongitud() {
        String bodyLargo = "a".repeat(251);
        note nota = new note(1, bodyLargo);
        assertNull(nota.getBody());
    }

    @Test
    void testChangeBodyNull() {
        note nota = new note(1, "Cuerpo original");
        nota.changeBody(null);
        assertNull(nota.getBody());
    }

    @Test
    void testGetLastUpdateDate() {
        note nota = new note(1, "Cuerpo original");
        assertNotNull(nota.getLastUpdateDate());
    }
}