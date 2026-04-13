package com.itacademy.cliagenda.application.menu;

import com.itacademy.cliagenda.common.utils.ConsoleUtils;
import com.itacademy.cliagenda.event.cli.EventCli;
import com.itacademy.cliagenda.note.cli.NoteCli;
import com.itacademy.cliagenda.task.cli.TaskCli;

import java.util.Scanner;

public class AppMenu {

    Scanner scanner = new Scanner(System.in);
    int userOption = -1;
    EventCli eventCli = new EventCli();
    TaskCli taskCli = new TaskCli();
    NoteCli noteCli = new NoteCli();

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
                    taskCli.showMenu();
                    break;
                case (2):
                    noteCli.showMenu();
                    break;
                case (3):
                    eventCli.showMenu();
                    break;
            }
        } while (userOption != 0);
        System.out.println("Bye my friend...");
    }

}