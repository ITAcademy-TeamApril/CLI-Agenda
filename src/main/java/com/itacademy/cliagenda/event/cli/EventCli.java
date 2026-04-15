package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EventCli {

    private final EventService service;
    private final Scanner scanner = new Scanner(System.in);

    public EventCli(EventService service) {
        this.service = service;
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< EVENTS MENU >>");
            System.out.println("1 - Create event");
            System.out.println("2 - List events");
            System.out.println("3 - Find event");
            System.out.println("4 - Delete event");
            System.out.println("0 - Return to App Menu");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    listEvents();
                    break;
                case 3:
                    findEvent();
                    break;
                case 4:
                    deleteEvent();
                    break;
            }
        } while (option != 0);
    }

    public void createEvent() {
        System.out.println("Introduce Event title:");
        String title = scanner.nextLine();
        System.out.println("Introduce Event description:");
        String description = scanner.nextLine();
        System.out.println("Introduce date (yyyy-MM-dd HH:mm):");
        String dateText = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(dateText,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.println("Recurring? (Y/N):");
        boolean recurring = scanner.nextLine().equalsIgnoreCase("y");

        Event event = service.createEvent(title, description, dateTime, recurring);
        System.out.println("Event \"" + event.getTitle() + "\" created.");
    }

    public void listEvents() {
        List<Event> events = service.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        for (Event event : events) {
            System.out.println("ID: " + event.getId()
                    + " | " + event.getTitle()
                    + " | " + event.getDateTimeEvent()
                    + " | Recurring: " + event.isRecurring());
        }
    }

    public void findEvent() {
        System.out.println("Introduce event ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Event event = service.findEventById(id);
        if (event == null) {
            System.out.println("Event not found.");
        } else {
            System.out.println("ID: " + event.getId());
            System.out.println("Title: " + event.getTitle());
            System.out.println("Description: " + event.getDescription());
            System.out.println("Date: " + event.getDateTimeEvent());
            System.out.println("Recurring: " + event.isRecurring());
        }
    }

    public void deleteEvent() {
        System.out.println("Introduce event ID to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        service.deleteEventById(id);
        System.out.println("Event deleted.");
    }
}