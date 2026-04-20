package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestEventService {

    private EventService service;
    private EventRepository repo;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        repo = new EventRepository();
        service = new EventService(repo);
    }

    @Test
    void testGetAllEventsEmpty() {
        List<Event> events = service.getAllEvents();
        assertNotNull(events);
        assertTrue(events.isEmpty());
    }

    @Test
    void testCreateEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Nuevo evento", "Descripcion", dateTime, false, false, 0);

        assertNotNull(event);
        assertEquals("Nuevo evento", event.getTitle());

        List<Event> events = service.getAllEvents();
        assertEquals(1, events.size());
    }

    @Test
    void testDeleteEventById() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento para borrar", "Descripcion", dateTime, false, false, 0);

        service.deleteEventById(event.getId());

        assertNull(service.findEventById(event.getId()));
    }

    @Test
    void testUpdateEvent() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento original", "Descripcion", dateTime, false, false, 0);

        event.changeTitle("Evento actualizado");
        service.updateEvent(event);

        Event updated = service.findEventById(event.getId());
        assertNotNull(updated);
        assertEquals("Evento actualizado", updated.getTitle());
    }

    @Test
    void testFindEventById() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento buscado", "Descripcion", dateTime, false, false, 0);

        Event found = service.findEventById(event.getId());

        assertNotNull(found);
        assertEquals("Evento buscado", found.getTitle());
    }

    @Test
    void testFindEventByIdNotFound() {
        Event event = service.findEventById(999);
        assertNull(event);
    }

    @Test
    void testGetNextRecurrenciesAnnual() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento anual", "Descripcion", dateTime, true, true, 0);
        
        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);
        
        assertEquals(5, recurrencies.size());
        assertEquals(2026, recurrencies.get(0).getYear());
    }

    @Test
    void testGetNextRecurrenciesMonthly() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento mensual", "Descripcion", dateTime, true, false, 3);
        
        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);
        
        assertEquals(5, recurrencies.size());
        assertEquals(2025, recurrencies.get(0).getYear());
        assertEquals(9, recurrencies.get(0).getMonthValue());
    }

    @Test
    void testGetNextRecurrenciesNonRecurring() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);
        Event event = service.createEvent("Evento no recurrente", "Descripcion", dateTime, false, false, 0);
        
        List<LocalDateTime> recurrencies = service.getNextRecurrencies(event);
        
        assertTrue(recurrencies.isEmpty());
    }
}