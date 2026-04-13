package com.itacademy.cliagenda.event.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

class TestEvent {

    private final LocalDateTime defaultDate = LocalDateTime.of(2025, 8, 15, 20, 0);

    @Test
    void testGetIdEvent() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        assertEquals(1, event.getIdEvent());
    }

    @Test
    void testGetTitle() {
        Event event = new Event(1, "Descripció", "Títol vàlid", defaultDate, false);
        assertEquals("Títol vàlid", event.getTitle());
    }

    @Test
    void testGetDescription() {
        Event event = new Event(1, "Descripció vàlida", "Títol", defaultDate, false);
        assertEquals("Descripció vàlida", event.getDescription());
    }

    @Test
    void testGetDateTimeEvent() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        assertEquals(defaultDate, event.getDateTimeEvent());
    }

    @Test
    void testIsRecurringFalse() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        assertFalse(event.isRecurring());
    }

    @Test
    void testIsRecurringTrue() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeTitleValid() {
        Event event = new Event(1, "Descripció", "Títol original", defaultDate, false);
        event.changeTitle("Títol nou");
        assertEquals("Títol nou", event.getTitle());
    }

    @Test
    void testChangeTitleNull() {
        Event event = new Event(1, "Descripció", "Títol original", defaultDate, false);
        event.changeTitle(null);
        assertEquals("Títol original", event.getTitle());
    }

    @Test
    void testChangeTitleTooLong() {
        Event event = new Event(1, "Descripció", "Títol original", defaultDate, false);
        String titleLlarg = "a".repeat(100);
        event.changeTitle(titleLlarg);
        assertEquals("Títol original", event.getTitle());
    }

    @Test
    void testChangeDescriptionValid() {
        Event event = new Event(1, "Descripció original", "Títol", defaultDate, false);
        event.changeDescription("Descripció nova");
        assertEquals("Descripció nova", event.getDescription());
    }

    @Test
    void testChangeDescriptionNull() {
        Event event = new Event(1, "Descripció original", "Títol", defaultDate, false);
        event.changeDescription(null);
        assertNull(event.getDescription());
    }

    @Test
    void testChangeDescriptionTooLong() {
        Event event = new Event(1, "Descripció original", "Títol", defaultDate, false);
        String descLlarga = "a".repeat(500);
        event.changeDescription(descLlarga);
        assertEquals("Descripció original", event.getDescription());
    }

    @Test
    void testConstructorFullParams() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 15, 10, 0);
        List<Integer> taskIds = List.of(1, 2, 3);
        Event event = new Event(1, "Descripció", "Títol", dateTime, true, taskIds);

        assertEquals(1, event.getIdEvent());
        assertEquals("Descripció", event.getDescription());
        assertEquals("Títol", event.getTitle());
        assertEquals(dateTime, event.getDateTimeEvent());
        assertTrue(event.isRecurring());
        assertEquals(List.of(1, 2, 3), event.getTaskIds());
    }

    @Test
    void testSetRecurring() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        event.setRecurring(true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testSetTaskIds() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        List<Integer> taskIds = List.of(1, 2);
        event.setTaskIds(taskIds);
        assertEquals(List.of(1, 2), event.getTaskIds());
    }

    @Test
    void testEmptyTaskIdsOnShortConstructor() {
        Event event = new Event(1, "Descripció", "Títol", defaultDate, false);
        assertTrue(event.getTaskIds().isEmpty());
    }
}