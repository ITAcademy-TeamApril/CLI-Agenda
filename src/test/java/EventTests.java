package com.itacademy.cliagenda.event.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testEventGettersAndSetters() {
        // Datos iniciales
        int id = 1;
        String description = "Birthday";
        String title = "John's Birthday";
        LocalDateTime dateTime = LocalDateTime.of(2026, 4, 10, 18, 0);
        boolean recurring = true;
        List<Integer> taskIds = new ArrayList<>();
        taskIds.add(101);
        taskIds.add(102);

        // Crear objeto Event
        Event event = new Event(id, description, title, dateTime, recurring, taskIds);

        // Verificar getters
        assertEquals(id, event.getIdEvent());
        assertEquals(description, event.getDescription());
        assertEquals(title, event.getTitle());
        assertEquals(dateTime, event.getDateTimeEvent());
        assertTrue(event.isRecurring());
        assertEquals(taskIds, event.getTaskIds());

        // Probar setters
        event.setIdEvent(2);
        event.setDescription("Meeting");
        event.setTitle("Project Meeting");
        LocalDateTime newDateTime = LocalDateTime.of(2026, 4, 11, 10, 0);
        event.setDateTimeEvent(newDateTime);
        event.setRecurring(false);
        List<Integer> newTaskIds = new ArrayList<>();
        newTaskIds.add(201);
        event.setTaskIds(newTaskIds);

        // Verificar cambios
        assertEquals(2, event.getIdEvent());
        assertEquals("Meeting", event.getDescription());
        assertEquals("Project Meeting", event.getTitle());
        assertEquals(newDateTime, event.getDateTimeEvent());
        assertFalse(event.isRecurring());
        assertEquals(newTaskIds, event.getTaskIds());
    }
}