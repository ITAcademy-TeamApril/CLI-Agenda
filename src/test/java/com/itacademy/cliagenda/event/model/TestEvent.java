package com.itacademy.cliagenda.event.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class TestEvent {

    private final LocalDateTime defaultDate = LocalDateTime.of(2025, 8, 15, 20, 0);

    @Test
    void testGetIdEvent() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        assertEquals(1, event.getId());
    }

    @Test
    void testGetTitle() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        assertEquals("Título", event.getTitle());
    }

    @Test
    void testGetDescription() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        assertEquals("Descripción", event.getDescription());
    }

    @Test
    void testGetDateTimeEvent() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        assertEquals(defaultDate, event.getDateTimeEvent());
    }

    @Test
    void testIsRecurringFalse() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        assertFalse(event.isRecurring());
    }

    @Test
    void testIsRecurringTrue() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, true, false, 0);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeTitleValid() {
        Event event = new Event(1, "Título original", "Descripción", defaultDate, false, false, 0);
        event.changeTitle("Título nuevo");
        assertEquals("Título nuevo", event.getTitle());
    }

    @Test
    void testChangeTitleNull() {
        Event event = new Event(1, "Título original", "Descripción", defaultDate, false, false, 0);
        event.changeTitle(null);
        assertEquals("Título original", event.getTitle());
    }

    @Test
    void testChangeTitleTooLong() {
        Event event = new Event(1, "Título original", "Descripción", defaultDate, false, false, 0);
        String titleLargo = "a".repeat(100);
        event.changeTitle(titleLargo);
        assertEquals("Título original", event.getTitle());
    }

    @Test
    void testChangeDescriptionValid() {
        Event event = new Event(1, "Título", "Descripción original", defaultDate, false, false, 0);
        event.changeDescription("Descripción nueva");
        assertEquals("Descripción nueva", event.getDescription());
    }

    @Test
    void testChangeDescriptionNull() {
        Event event = new Event(1, "Título", "Descripción original", defaultDate, false, false, 0);
        event.changeDescription(null);
        assertNull(event.getDescription());
    }

    @Test
    void testChangeDescriptionTooLong() {
        Event event = new Event(1, "Título", "Descripción original", defaultDate, false, false, 0);
        String descLarga = "a".repeat(500);
        event.changeDescription(descLarga);
        assertEquals("Descripción original", event.getDescription());
    }

    @Test
    void testSetRecurring() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        event.setRecurring(true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeDateEvent() {
        LocalDateTime newDate = LocalDateTime.of(2026, 1, 1, 12, 0);
        Event event = new Event(1, "Título", "Descripción", defaultDate, false, false, 0);
        event.changeDateEvent(newDate);
        assertEquals(newDate, event.getDateTimeEvent());
    }
}