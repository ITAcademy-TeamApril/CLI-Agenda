package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventCli {

    private final EventService service;
    private final Scanner scanner = new Scanner(System.in);

    public EventCli(EventService service) {
        this.service = service;
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
}