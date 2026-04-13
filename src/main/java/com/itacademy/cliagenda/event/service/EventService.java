package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EventService {
    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public Event createEvent(String title, String description,
                             LocalDateTime dateTime, boolean recurring) {
        int idEvent = generateNextId();
        Event newEvent = new Event(idEvent, description, title, dateTime, recurring);
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
            if (event.getIdEvent() > maxId) {
                maxId = event.getIdEvent();
            }
        }
        return maxId + 1;
    }



}
