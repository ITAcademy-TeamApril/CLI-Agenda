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
        java.time.LocalDateTime eventDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        Event event = new Event(5, "Evento prueba", eventDate);
        nota.setEvent_fk(event);
        assertEquals(5, nota.getEvent_fk());
    }

    @Test
    void testSetEvent_fkNegativo() {
        Note nota = new Note(1, "Cuerpo de prueba");
        java.time.LocalDateTime eventDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        nota.setEvent_fk(new Event(5, "Evento prueba", eventDate));
        nota.setEvent_fk(new Event(-1, "Evento negativo", eventDate));
        assertEquals(5, nota.getEvent_fk());
    }

    @Test
    void testSetEvent_fkCero() {
        Note nota = new Note(1, "Cuerpo de prueba");
        java.time.LocalDateTime eventDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        nota.setEvent_fk(new Event(0, "Evento cero", eventDate));
        assertEquals(0, nota.getEvent_fk());
    }

    @Test
    void testConstructorFullParams() {
        java.time.LocalDateTime creationDate = java.time.LocalDateTime.of(2024, 1, 1, 10, 0);
        java.time.LocalDateTime lastUpdateDate = java.time.LocalDateTime.of(2024, 1, 2, 12, 0);
        Note nota = new Note(1, "Cuerpo válido", creationDate, lastUpdateDate, 5);
        
        assertEquals(1, nota.getId());
        assertEquals("Cuerpo válido", nota.getBody());
        assertEquals(creationDate.toString(), nota.getCreationDate());
        assertEquals(lastUpdateDate, nota.getLastUpdateDate());
        assertEquals(5, nota.getEvent_fk());
    }

    @Test
    void testLastUpdateDateActualizado() {
        Note nota = new Note(1, "Cuerpo original");
        java.time.LocalDateTime fechaAntes = nota.getLastUpdateDate();
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        nota.changeBody("Cuerpo actualizado");
        java.time.LocalDateTime fechaDespues = nota.getLastUpdateDate();
        
        assertNotNull(fechaDespues);
        assertTrue(fechaDespues.isAfter(fechaAntes) || fechaDespues.isEqual(fechaAntes));
    }

    @Test
    void testLastUpdateDateNoNuloTrasChangeBody() {
        Note nota = new Note(1, "Cuerpo original");
        nota.changeBody("Cuerpo actualizado");
        assertNotNull(nota.getLastUpdateDate());
    }
}