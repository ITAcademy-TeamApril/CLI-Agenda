package com.itacademy.cliagenda.event.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

class TestEvent {

    @Test
    void testGetIdEvent() {
        Event event = new Event(1, "Descripció", "Títol", false);
        assertEquals(1, event.getIdEvent());
    }

    @Test
    void testGetTitle() {
        Event event = new Event(1, "Descripció", "Títol vàlid", false);
        assertEquals("Títol vàlid", event.getTitle());
    }

    @Test
    void testGetDescription() {
        Event event = new Event(1, "Descripció vàlida", "Títol", false);
        assertEquals("Descripció vàlida", event.getDescription());
    }

    @Test
    void testGetDateTimeEventNotNull() {
        Event event = new Event(1, "Descripció", "Títol", false);
        assertNotNull(event.getDateTimeEvent());
    }

    @Test
    void testIsRecurringFalse() {
        Event event = new Event(1, "Descripció", "Títol", false);
        assertFalse(event.isRecurring());
    }

    @Test
    void testIsRecurringTrue() {
        Event event = new Event(1, "Descripció", "Títol", true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testChangeTitleValid() {
        Event event = new Event(1, "Descripció", "Títol original", false);
        event.changeTitle("Títol nou");
        assertEquals("Títol nou", event.getTitle());
    }

    @Test
    void testChangeTitleNull() {
        Event event = new Event(1, "Descripció", "Títol original", false);
        event.changeTitle(null);
        assertEquals("Títol original", event.getTitle());
    }

    @Test
    void testChangeTitleTooLong() {
        Event event = new Event(1, "Descripció", "Títol original", false);
        String titleLlarg = "a".repeat(100);
        event.changeTitle(titleLlarg);
        assertEquals("Títol original", event.getTitle());
    }

    @Test
    void testChangeDescriptionValid() {
        Event event = new Event(1, "Descripció original", "Títol", false);
        event.changeDescription("Descripció nova");
        assertEquals("Descripció nova", event.getDescription());
    }

    @Test
    void testChangeDescriptionNull() {
        Event event = new Event(1, "Descripció original", "Títol", false);
        event.changeDescription(null);
        assertNull(event.getDescription());
    }

    @Test
    void testChangeDescriptionTooLong() {
        Event event = new Event(1, "Descripció original", "Títol", false);
        String descLlarga = "a".repeat(500);
        event.changeDescription(descLlarga);
        assertEquals("Descripció original", event.getDescription());
    }

    @Test
    void testConstructorTitleNull() {
        Event event = new Event(1, "Descripció", null, false);
        assertNull(event.getTitle());
    }

    @Test
    void testConstructorTitleTooLong() {
        String titleLlarg = "a".repeat(100);
        Event event = new Event(1, "Descripció", titleLlarg, false);
        assertNull(event.getTitle());
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
        Event event = new Event(1, "Descripció", "Títol", false);
        event.setRecurring(true);
        assertTrue(event.isRecurring());
    }

    @Test
    void testSetTaskIds() {
        Event event = new Event(1, "Descripció", "Títol", false);
        List<Integer> taskIds = List.of(1, 2);
        event.setTaskIds(taskIds);
        assertEquals(List.of(1, 2), event.getTaskIds());
    }
}