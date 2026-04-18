package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EventCli {

    private final EventService service;
    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    public EventCli(EventService service, TaskService taskService) {
        this.service = service;
        this.taskService = taskService;
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< EVENTS MENU >>");
            System.out.println("1 - Create event");
            System.out.println("2 - List events");
            System.out.println("3 - Find event");
            System.out.println("4 - Update event");
            System.out.println("5 - Delete event");
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
                    updateEvent();
                    break;
                case 5:
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

        LocalDateTime dateTime = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("Introduce date (yyyy-MM-dd HH:mm):");
            String dateText = scanner.nextLine();
            try {
                dateTime = LocalDateTime.parse(dateText,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                validDate = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Use the format yyyy-MM-dd HH:mm");
            }
        }

        System.out.println("Recurring? (Y/N):");
        boolean recurring = scanner.nextLine().equalsIgnoreCase("y");

        boolean annualRecurring = false;
        int recurrenceInterval = 0;

        if (recurring) {
            System.out.println("Annual recurring? (Y/N):");
            annualRecurring = scanner.nextLine().equalsIgnoreCase("y");
            if (!annualRecurring) {
                System.out.println("Recurrence interval in months:");
                recurrenceInterval = Integer.parseInt(scanner.nextLine());
            }
        }

        Event event = service.createEvent(title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
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
                    + " | " + (event.isRecurring() ? (event.isAnnualRecurring() ?
                    "Recurring: yearly" : "Recurring: each "
                    + event.getRecurrenceInterval() + " months") : "Not recurring"));
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
            if (event.isRecurring()) {
                if (event.isAnnualRecurring()) {
                    System.out.println("Recurrence: yearly");
                } else {
                    System.out.println("Recurrence: each " + event.getRecurrenceInterval() + " months");
                }
                System.out.println("Next recurrencies:");
                List<LocalDateTime> dates = service.getNextRecurrencies(event);
                for (LocalDateTime date : dates) {
                    System.out.println("  - " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }

            List<Task> tasks = taskService.getTasksByEventId(id);
            if (!tasks.isEmpty()) {
                System.out.println("\nAssociated tasks:");
                for (Task task : tasks) {
                    System.out.println("  ID: " + task.getId() + " - " + task.getBody()
                            + " (Completed: " + (task.isCompleted() ? "Yes" : "No") + ")");
                }
            } else {
                System.out.println("\nNo associated tasks.");
            }
        }
    }

    public void deleteEvent() {
        System.out.println("Introduce event ID to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        service.deleteEventById(id);
        System.out.println("Event deleted.");
    }

    public void updateEvent() {
        System.out.println("Introduce event ID to update:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Event event = service.findEventById(id);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("Current event:");
        System.out.println("  ID: " + event.getId());
        System.out.println("  Title: " + event.getTitle());
        System.out.println("  Description: " + event.getDescription());
        System.out.println("  Date: " + event.getDateTimeEvent());
        System.out.println("  Recurring: " + (event.isRecurring() ? "Yes" : "No"));
        System.out.println();

        System.out.println("Do you want to modify the title? (Y/N):");
        String modifyTitle = scanner.nextLine();
        if (modifyTitle.equalsIgnoreCase("y")) {
            System.out.println("Introduce new title:");
            String newTitle = scanner.nextLine();
            event.changeTitle(newTitle);
        }

        System.out.println("Do you want to modify the description? (Y/N):");
        String modifyDesc = scanner.nextLine();
        if (modifyDesc.equalsIgnoreCase("y")) {
            System.out.println("Introduce new description:");
            String newDesc = scanner.nextLine();
            event.changeDescription(newDesc);
        }

        System.out.println("Do you want to modify the date? (Y/N):");
        String modifyDate = scanner.nextLine();
        if (modifyDate.equalsIgnoreCase("y")) {
            LocalDateTime newDateTime = null;
            boolean validDate = false;
            while (!validDate) {
                System.out.println("Introduce new date (yyyy-MM-dd HH:mm):");
                String dateText = scanner.nextLine();
                try {
                    newDateTime = LocalDateTime.parse(dateText,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    validDate = true;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use the format yyyy-MM-dd HH:mm");
                }
            }
            event.changeDateEvent(newDateTime);
        }

        System.out.println("Do you want to modify the recurring status? (Y/N):");
        String modifyRecurring = scanner.nextLine();
        if (modifyRecurring.equalsIgnoreCase("y")) {
            System.out.println("Mark as recurring? (Y/N):");
            boolean recurring = scanner.nextLine().equalsIgnoreCase("y");
            event.setRecurring(recurring);

            if (recurring) {
                System.out.println("Annual recurring? (Y/N):");
                boolean annualRecurring = scanner.nextLine().equalsIgnoreCase("y");
                event.setAnnualRecurring(annualRecurring);
                if (!annualRecurring) {
                    System.out.println("Recurrence interval in months:");
                    int recurrenceInterval = Integer.parseInt(scanner.nextLine());
                    event.setRecurrenceInterval(recurrenceInterval);
                }
            }
        }

        service.updateEvent(event);
        System.out.println("Event updated successfully.");
    }
}