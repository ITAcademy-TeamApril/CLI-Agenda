package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.common.utils.ConsoleUtils;

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
    }
}