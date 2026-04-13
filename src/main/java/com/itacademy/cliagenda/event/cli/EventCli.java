package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.common.utils.ConsoleUtils;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EventCli {

    private final Scanner scanner = new Scanner(System.in);

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
                case (1):
                    // TODO: createEvent()
                    break;
                case (2):
                    // TODO: listEvents()
                    break;
                case (3):
                    // TODO: findEvent()
                    break;
                case (4):
                    // TODO: deleteEvent()
                    break;
            }
        } while (option != 0);
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