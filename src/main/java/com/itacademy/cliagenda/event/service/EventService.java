package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;

import java.time.LocalDateTime;
import java.util.List;


public class EventService {
    private final EventRepository repo;
    private final SqlDao dao;

    public EventService(EventRepository repo) {
        this.repo = repo;
        this.dao = SqlDao.getInstance();
        List<Event> eventsFromDb = dao.findAllEvents();
        for (Event event : eventsFromDb) {
            repo.save(event);
        }
    }

    public Event createEvent(String title, String description,
                             LocalDateTime dateTime, boolean recurring) {
        int idEvent = generateNextId();
        Event newEvent = new Event(idEvent, title, description, dateTime, recurring);
        dao.saveEvents(newEvent);
        repo.save(newEvent);
        return newEvent;
    }

    public List<Event> getAllEvents() {
        return repo.getAllEvents();
    }

    private int generateNextId() {
        List<Event> events = repo.getAllEvents();
        int maxId = 0;
        for (Event event : events) {
            if (event.getId() > maxId) {
                maxId = event.getId();
            }
        }
        return maxId + 1;
    }

    public Event findEventById(int id) {
        return repo.findEventById(id);
    }

    public void deleteEventById(int id) {
        dao.deleteEvent(id);
        repo.removeEventById(id);
    }

    public void updateEvent(Event event) {
        dao.updateEvent(event);
        repo.removeEventById(event.getId());
        repo.save(event);
    }

}