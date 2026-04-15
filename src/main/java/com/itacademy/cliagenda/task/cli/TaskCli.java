package com.itacademy.cliagenda.task.cli;

import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
                    break;
                case (2):
                    listTasks();
                    break;
                case (3):
                    findTask();
                    break;
                case (4):
                    deleteTask();
                    break;
            }
        } while (option != 0);
    }

    public void createTask() {

        System.out.println("Introduce task");
        String name = scanner.nextLine();
        System.out.println("Introduce task date Time with format \"yyyy-MM-dd HH:mm\"");
        String dateText = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(dateText,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Task task = service.createTask(name, dateTime);
        System.out.println("Task \"" + task.getName() + "\" created.");

    }

    public void listTasks() {
        List<Task> tasks = service.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }
        for (Task task : tasks) {
            System.out.println("ID: " + task.getId()
                    + " | " + task.getName()
                    + " | " + task.getCreationDate());
        }
    }

    public void findTask() {
        System.out.println("Introduce task ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Task task = service.findTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
        } else {
            System.out.println("ID: " + task.getId());
            System.out.println("Name: " + task.getName());
            System.out.println("Date: " + task.getCreationDate());
        }
    }

    public void deleteTask() {
        System.out.println("Introduce task ID to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        service.deleteTaskById(id);
        System.out.println("Task deleted.");
    }
}