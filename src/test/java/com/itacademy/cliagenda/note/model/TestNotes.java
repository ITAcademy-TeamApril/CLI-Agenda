package com.itacademy.cliagenda.note.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestNotes {

    @Test
    void testGetId() {
        Note nota = new Note(1, "Cuerpo de prueba");
        assertEquals(1, nota.getId());
    }

    @Test
    void testGetBody() {
        Note nota = new Note(1, "Cuerpo de prueba");
        assertEquals("Cuerpo de prueba", nota.getBody());
    }

    @Test
    void testGetCreationDate() {
        Note nota = new Note(1, "Cuerpo de prueba");
        assertNotNull(nota.getCreationDate());
    }

    @Test
    void testChangeBodyValido() {
        Note nota = new Note(1, "Cuerpo original");
        nota.changeBody("Cuerpo actualizado");
        assertEquals("Cuerpo actualizado", nota.getBody());
    }

    @Test
    void testChangeBodyExcedeLongitud() {
        Note nota = new Note(1, "Cuerpo original");
        String bodyLargo = "a".repeat(251);
        nota.changeBody(bodyLargo);
        assertEquals("Cuerpo original", nota.getBody());
    }

    @Test
    void testConstructorBodyValido() {
        Note nota = new Note(1, "Cuerpo válido");
        assertEquals("Cuerpo válido", nota.getBody());
    }

    @Test
    void testConstructorBodyNulo() {
        Note nota = new Note(1, null);
        assertNull(nota.getBody());
    }

    @Test
    void testConstructorBodyExcedeLongitud() {
        String bodyLargo = "a".repeat(251);
        Note nota = new Note(1, bodyLargo);
        assertNull(nota.getBody());
    }

    @Test
    void testChangeBodyNull() {
        Note nota = new Note(1, "Cuerpo original");
        nota.changeBody(null);
        assertNull(nota.getBody());
    }

    @Test
    void testGetLastUpdateDate() {
        Note nota = new Note(1, "Cuerpo original");
        assertNotNull(nota.getLastUpdateDate());
    }

    @Test
    void testGetEvent_fk() {
        Note nota = new Note(1, "Cuerpo de prueba");
        assertEquals(0, nota.getEvent_fk());
    }

    @Test
    void testSetEvent_fkValido() {
        Note nota = new Note(1, "Cuerpo de prueba");
        nota.setEvent_fk(5);
        assertEquals(5, nota.getEvent_fk());
    }

    @Test
    void testSetEvent_fkNegativo() {
        Note nota = new Note(1, "Cuerpo de prueba");
        nota.setEvent_fk(5);
        nota.setEvent_fk(-1);
        assertEquals(5, nota.getEvent_fk());
    }
}