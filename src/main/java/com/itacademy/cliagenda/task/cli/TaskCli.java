package com.itacademy.cliagenda.task.cli;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TaskCli {

    private final TaskService service;
    private final Scanner scanner = new Scanner(System.in);

    public TaskCli(TaskService service) {
        this.service = service;
    }

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
                    createTask();
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

    public void createTask() {

        System.out.println("Introduce task");
        String name = scanner.nextLine();
        System.out.println("Introduce task date Time");
        String dateText = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(dateText,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Task task = new Task();

    }
}