package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestEventRepository {

    private EventRepository repo;

    @BeforeEach
    void setUp() {
        repo = new EventRepository();
    }

    @Test
    void testSaveEvent() {
        Event event = new Event(1, "Desc", "Títol", false);
        repo.save(event);
        assertEquals(1, repo.getAllEvents().size());
    }

    @Test
    void testGetAllEventsReturnsACopy() {
        Event event = new Event(1, "Desc", "Títol", false);
        repo.save(event);
        List<Event> events = repo.getAllEvents();
        assertThrows(UnsupportedOperationException.class, () -> events.add(new Event(2, "Desc2", "Títol2", false)));
    }

    @Test
    void testGetAllEventsEmpty() {
        assertTrue(repo.getAllEvents().isEmpty());
    }

    @Test
    void testFindEventByIdFound() {
        Event event = new Event(1, "Desc", "Títol", false);
        repo.save(event);
        Event found = repo.findEventById(1);
        assertEquals(1, found.getIdEvent());
    }

    @Test
    void testFindEventByIdNotFound() {
        assertNull(repo.findEventById(99));
    }

    @Test
    void testRemoveEventById() {
        Event event = new Event(1, "Desc", "Títol", false);
        repo.save(event);
        repo.removeEventById(1);
        assertTrue(repo.getAllEvents().isEmpty());
    }

    @Test
    void testRemoveEventByIdNotFound() {
        Event event = new Event(1, "Desc", "Títol", false);
        repo.save(event);
        repo.removeEventById(99);
        assertEquals(1, repo.getAllEvents().size());
    }

    @Test
    void testSaveMultipleEvents() {
        repo.save(new Event(1, "Desc1", "Títol1", false));
        repo.save(new Event(2, "Desc2", "Títol2", true));
        repo.save(new Event(3, "Desc3", "Títol3", false));
        assertEquals(3, repo.getAllEvents().size());
    }
}