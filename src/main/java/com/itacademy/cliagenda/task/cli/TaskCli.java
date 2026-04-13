package com.itacademy.cliagenda.task.cli;

import java.util.Scanner;

public class TaskCli {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< TASKS MENU >>");
            System.out.println("1 - Create task");
            System.out.println("2 - List tasks");
            System.out.println("3 - Find task");
            System.out.println("4 - Delete task");
            System.out.println("0 - Return to App Menu");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case (1):
                    // TODO: createTask()
                    break;
                case (2):
                    // TODO: listTasks()
                    break;
                case (3):
                    // TODO: findTask()
                    break;
                case (4):
                    // TODO: deleteTask()
                    break;
            }
        } while (option != 0);
    }
}