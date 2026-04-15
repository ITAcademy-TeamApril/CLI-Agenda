package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestEventRepository {

    private EventRepository repo;
    private final LocalDateTime defaultDate = LocalDateTime.of(2025, 6, 15, 10, 0);

    @BeforeEach
    void setUp() {
        repo = new EventRepository();
    }

    @Test
    void testSaveEvent() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false);
        repo.save(event);
        assertEquals(1, repo.getAllEvents().size());
    }

    @Test
    void testGetAllEventsReturnsACopy() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false);
        repo.save(event);
        List<Event> events = repo.getAllEvents();
        assertThrows(UnsupportedOperationException.class, () -> events.add(new Event(2, "Título2", "Descripción2", defaultDate, false)));
    }

    @Test
    void testGetAllEventsEmpty() {
        assertTrue(repo.getAllEvents().isEmpty());
    }

    @Test
    void testFindEventByIdFound() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false);
        repo.save(event);
        Event found = repo.findEventById(1);
        assertEquals(1, found.getId());
    }

    @Test
    void testFindEventByIdNotFound() {
        assertNull(repo.findEventById(99));
    }

    @Test
    void testRemoveEventById() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false);
        repo.save(event);
        repo.removeEventById(1);
        assertTrue(repo.getAllEvents().isEmpty());
    }

    @Test
    void testRemoveEventByIdNotFound() {
        Event event = new Event(1, "Título", "Descripción", defaultDate, false);
        repo.save(event);
        repo.removeEventById(99);
        assertEquals(1, repo.getAllEvents().size());
    }

    @Test
    void testSaveMultipleEvents() {
        repo.save(new Event(1, "Título1", "Descripción1", defaultDate, false));
        repo.save(new Event(2, "Título2", "Descripción2", defaultDate, true));
        repo.save(new Event(3, "Título3", "Descripción3", defaultDate, false));
        assertEquals(3, repo.getAllEvents().size());
    }
}