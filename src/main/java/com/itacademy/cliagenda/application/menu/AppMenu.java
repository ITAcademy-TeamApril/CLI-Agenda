package com.itacademy.cliagenda.application.menu;

import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.event.service.EventService;

import java.util.Scanner;

public class AppMenu {

    int userOption=-1;
    Scanner scanner = new Scanner(System.in);
    EventRepository repo = new EventRepository();
    EventService eventService = new EventService(repo);

    public void playMenu(){

        do {
            System.out.println("TASK / NOTES / EVENTS FUCKING APP\b");
            System.out.println("1 - TASKS");
            System.out.println("2 - NOTES");
            System.out.println("3 - EVENTS");
            System.out.println("0 - Exit App");
            System.out.println("\b select an option..");

            userOption = scanner.nextInt();

            switch (userOption){
                case(1):
                    menuTasKs();
                    break;
                case(2):
                    menuNotes();
                    break;
                case(3):
                    menuEvents();
                    break;
            }


        }while (userOption != 0);
        System.out.println("Bye my friend...");

    }

    public void menuTasKs() {
        int userOption=-1;

        do{
            System.out.println("<< TASKs MENU >>\b");
            System.out.println("1 - Create task");
            System.out.println("2 - List tasks");
            System.out.println("3 - Find task");
            System.out.println("4 - Delete task");
            System.out.println("0 - Return to App Menu");

            userOption = scanner.nextInt();
        }while (userOption !=0);
    }

    public void menuNotes() {
        int userOption=-1;

        do{
            System.out.println("<< NOTEs MENU >>\b");
            System.out.println("1 - Create note");
            System.out.println("2 - List notes");
            System.out.println("3 - Find note");
            System.out.println("4 - Delete notes");
            System.out.println("0 - Return to App Menu");

            userOption = scanner.nextInt();
        }while (userOption !=0);
    }

    public void menuEvents() {
        int userOption=-1;

        do{
            System.out.println("<< EVENTS MENU >>\b");
            System.out.println("1 - Create event");
            System.out.println("2 - List events");
            System.out.println("3 - Find events");
            System.out.println("4 - Delete event");
            System.out.println("0 - Return to App Menu");

            userOption = scanner.nextInt();

            switch (userOption) {
                case(1):
                    eventService.createEvent();
                    break;
                case(2):
                    eventService.getAllEvents();
                    break;
            }
        }while (userOption !=0);
    }
}
