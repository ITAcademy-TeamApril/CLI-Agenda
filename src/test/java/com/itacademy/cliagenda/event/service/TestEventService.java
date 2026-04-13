package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestEventService {

    private EventRepository repo;
    private EventService service;

    @BeforeEach
    void setUp() {
        repo = new EventRepository();
        service = new EventService(repo);
    }

    @Test
    void testGetAllEventsEmpty() {
        assertTrue(service.getAllEvents().isEmpty());
    }

    @Test
    void testGetAllEventsAfterSave() {
        repo.save(new Event(1, "Desc", "Títol", false));
        assertEquals(1, service.getAllEvents().size());
    }

    @Test
    void testCheckEventIdEmpty() {
        assertEquals(0, service.checkEventId());
    }

    @Test
    void testCheckEventIdWithEvents() {
        repo.save(new Event(1, "Desc1", "Títol1", false));
        repo.save(new Event(5, "Desc2", "Títol2", true));
        repo.save(new Event(3, "Desc3", "Títol3", false));
        assertEquals(5, service.checkEventId());
    }

    @Test
    void testCheckEventIdReturnsMax() {
        repo.save(new Event(10, "Desc", "Títol", false));
        repo.save(new Event(2, "Desc", "Títol", false));
        assertEquals(10, service.checkEventId());
    }
}