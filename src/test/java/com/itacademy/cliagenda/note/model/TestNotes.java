package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.task.model.Task;
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
    void testGetTask_fk() {
        Note nota = new Note(1, "Cuerpo de prueba");
        assertEquals(0, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkValido() {
        Note nota = new Note(1, "Cuerpo de prueba");
        java.time.LocalDateTime taskDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        Task task = new Task(5, "Evento prueba", taskDate);
        nota.setTask_fk(task);
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkNegativo() {
        Note nota = new Note(1, "Cuerpo de prueba");
        java.time.LocalDateTime taskDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        nota.setTask_fk(new Task(5, "Tarea prueba", taskDate));
        nota.setTask_fk(new Task(-1, "Tarea negativa", taskDate));
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkCero() {
        Note nota = new Note(1, "Cuerpo de prueba");
        java.time.LocalDateTime taskDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 0);
        nota.setTask_fk(new Task(0, "Tarea cero", taskDate));
        assertEquals(0, nota.getTask_fk());
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
        assertEquals(5, nota.getTask_fk());
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