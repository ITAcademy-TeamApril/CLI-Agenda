package com.itacademy.cliagenda.task.model;

import com.itacademy.cliagenda.event.model.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class TestTask {

    @Test
    void testGetId() {
        Task tarea = new Task(1, "Cuerpo de prueba", 1);
        assertEquals(1, tarea.getId());
    }

    @Test
    void testGetBody() {
        Task tarea = new Task(1, "Cuerpo de prueba", 1);
        assertEquals("Cuerpo de prueba", tarea.getBody());
    }

    @Test
    void testChangeBodyValido() {
        Task tarea = new Task(1, "Cuerpo original", 1);
        tarea.changeBody("Cuerpo actualizado");
        assertEquals("Cuerpo actualizado", tarea.getBody());
    }

    @Test
    void testChangeBodyExcedeLongitud() {
        Task tarea = new Task(1, "Cuerpo original", 1);
        String bodyLargo = "a".repeat(251);
        tarea.changeBody(bodyLargo);
        assertEquals("Cuerpo original", tarea.getBody());
    }

    @Test
    void testConstructorBodyValido() {
        Task tarea = new Task(1, "Cuerpo válido", 1);
        assertEquals("Cuerpo válido", tarea.getBody());
    }

    @Test
    void testConstructorBodyNulo() {
        Task tarea = new Task(1, null, 0);
        assertNull(tarea.getBody());
    }

    @Test
    void testChangeBodyNull() {
        Task tarea = new Task(1, "Cuerpo original", 1);
        tarea.changeBody(null);
        assertNull(tarea.getBody());
    }

    @Test
    void testGetEvent_fk() {
        Task tarea = new Task(1, "Cuerpo de prueba", 5);
        assertEquals(5, tarea.getEvent_fk());
    }

    @Test
    void testGetEvent_fkDefault() {
        Task tarea = new Task(1, "Cuerpo de prueba");
        assertEquals(0, tarea.getEvent_fk());
    }

    @Test
    void testSetEvent_fkValido() {
        Task tarea = new Task(1, "Cuerpo de prueba", 0);
        Event event = new Event(5, "Evento prueba", "Descripcion", LocalDateTime.of(2025, 6, 15, 10, 0), false);
        tarea.setEvent_fk(event);
        assertEquals(5, tarea.getEvent_fk());
    }

    @Test
    void testSetEvent_fkNegativo() {
        Task tarea = new Task(1, "Cuerpo de prueba", 0);
        Event event = new Event(5, "Evento prueba", "Descripcion", LocalDateTime.of(2025, 6, 15, 10, 0), false);
        tarea.setEvent_fk(event);
        tarea.setEvent_fk(new Event(-1, "Evento negativo", "Descripcion", LocalDateTime.of(2025, 6, 15, 10, 0), false));
        assertEquals(5, tarea.getEvent_fk());
    }

    @Test
    void testSetEvent_fkCero() {
        Task tarea = new Task(1, "Cuerpo de prueba", 0);
        Event event = new Event(0, "Evento cero", "Descripcion", LocalDateTime.of(2025, 6, 15, 10, 0), false);
        tarea.setEvent_fk(event);
        assertEquals(0, tarea.getEvent_fk());
    }

    @Test
    void testConstructorWithEventObject() {
        Event event = new Event(5, "Evento prueba", "Descripcion", LocalDateTime.of(2025, 6, 15, 10, 0), false);
        Task tarea = new Task(1, "Cuerpo", event);
        
        assertEquals(5, tarea.getEvent_fk());
    }

    @Test
    void testSetEvent_fkWithNullDoesNotThrow() {
        Task tarea = new Task(1, "Cuerpo", 1);
        Event event = null;
        tarea.setEvent_fk(event);
    }

    @Test
    void testConstructorDosParametros() {
        Task tarea = new Task(1, "Tarea simple");
        
        assertEquals(1, tarea.getId());
        assertEquals("Tarea simple", tarea.getBody());
        assertEquals(0, tarea.getEvent_fk());
    }
}