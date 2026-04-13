package com.itacademy.cliagenda.application.menu;

import java.util.Scanner;

public class AppMenu {

    Scanner scanner = new Scanner(System.in);
    int userOption = -1;

    public void playMenu() {
        do {
            System.out.println("TASK / NOTES / EVENTS APP");
            System.out.println("1 - TASKS");
            System.out.println("2 - NOTES");
            System.out.println("3 - EVENTS");
            System.out.println("0 - Exit App");
            System.out.println("Select an option:");

            userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case (1):
                    // TODO: taskCli.showMenu()
                    break;
                case (2):
                    // TODO: noteCli.showMenu()
                    break;
                case (3):
                    // TODO: eventCli.showMenu()
                    break;
            }
        } while (userOption != 0);
        System.out.println("Bye my friend...");
    }
}