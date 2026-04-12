package com.itacademy.cliagenda.event.service;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class EventService {
    Scanner scanner = new Scanner(System.in);
    EventRepository repo = new EventRepository();

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    //CREATE
    public void createEvent() {
        String description;
        String title;
        boolean recurring;
        String userText;

        int idEvent = checkEventId() + 1;
        System.out.println("Introduce Event title");
        title = scanner.nextLine();
        System.out.println("Introduce Event description");
        description = scanner.nextLine();
        System.out.println("This event will be recurring?(Y or N)");
        userText = scanner.nextLine();
        recurring = false;
        if(userText.equalsIgnoreCase("y")) {
            recurring = true;
        }

        Event newEvent = new Event(idEvent, description, title, recurring);
        repo.save(newEvent);
        System.out.println("New event with title \"" + title +"\" has been created" );
    }

    public List<Event> getAllEvents(){

        return repo.getAllEvents();
    }

    int checkEventId() {
        List<Event> events = repo.getAllEvents();
        if (events.isEmpty()) {
            return 0;
        }
        int maxId = 0;
        for (Event event : events) {
            if (event.getIdEvent() > maxId) {
                maxId = event.getIdEvent();
            }
        }
        return maxId;
    }


}
