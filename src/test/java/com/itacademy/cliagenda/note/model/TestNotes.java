package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.task.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestNotes {

    @Test
    void testGetId() {
        Note nota = new Note(1, "Cuerpo de prueba", 1);
        assertEquals(1, nota.getId());
    }

    @Test
    void testGetBody() {
        Note nota = new Note(1, "Cuerpo de prueba", 1);
        assertEquals("Cuerpo de prueba", nota.getBody());
    }

    @Test
    void testChangeBodyValido() {
        Note nota = new Note(1, "Cuerpo original", 1);
        nota.changeBody("Cuerpo actualizado");
        assertEquals("Cuerpo actualizado", nota.getBody());
    }

    @Test
    void testChangeBodyExcedeLongitud() {
        Note nota = new Note(1, "Cuerpo original", 1);
        String bodyLargo = "a".repeat(251);
        nota.changeBody(bodyLargo);
        assertEquals("Cuerpo original", nota.getBody());
    }

    @Test
    void testConstructorBodyValido() {
        Note nota = new Note(1, "Cuerpo válido", 1);
        assertEquals("Cuerpo válido", nota.getBody());
    }

    @Test
    void testConstructorBodyNulo() {
        Note nota = new Note(1, null, 0);
        assertNull(nota.getBody());
    }

    @Test
    void testConstructorBodyExcedeLongitud() {
        String bodyLargo = "a".repeat(251);
        Note nota = new Note(1, bodyLargo, 1);
        assertNull(nota.getBody());
    }

    @Test
    void testChangeBodyNull() {
        Note nota = new Note(1, "Cuerpo original", 1);
        nota.changeBody(null);
        assertNull(nota.getBody());
    }

    @Test
    void testGetTask_fk() {
        Note nota = new Note(1, "Cuerpo de prueba", 5);
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testGetTask_fkDefault() {
        Note nota = new Note(1, "Cuerpo de prueba", 0);
        assertEquals(0, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkValido() {
        Note nota = new Note(1, "Cuerpo de prueba", 0);
        Task task = new Task(5, "Tarea prueba");
        nota.setTask_fk(task);
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkNegativo() {
        Note nota = new Note(1, "Cuerpo de prueba", 0);
        Task task = new Task(5, "Tarea prueba");
        nota.setTask_fk(task);
        nota.setTask_fk(new Task(-1, "Tarea negativa"));
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testSetTask_fkCero() {
        Note nota = new Note(1, "Cuerpo de prueba", 0);
        nota.setTask_fk(new Task(0, "Tarea cero"));
        assertEquals(0, nota.getTask_fk());
    }

    @Test
    void testConstructorWithTaskObject() {
        Task task = new Task(5, "Tarea prueba");
        Note nota = new Note(1, "Cuerpo", task);
        
        assertEquals(5, nota.getTask_fk());
    }

    @Test
    void testSetTaskWithNullDoesNotThrow() {
        Note nota = new Note(1, "Cuerpo", 1);
        Task task = null;
        nota.setTask_fk(task);
    }
}