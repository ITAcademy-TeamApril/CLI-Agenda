package com.itacademy.cliagenda.note.cli;

import java.util.Scanner;

public class NoteCli {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< NOTES MENU >>");
            System.out.println("1 - Create note");
            System.out.println("2 - List notes");
            System.out.println("3 - Find note");
            System.out.println("4 - Delete note");
            System.out.println("0 - Return to App Menu");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case (1):
                    // TODO: createNote()
                    break;
                case (2):
                    // TODO: listNotes()
                    break;
                case (3):
                    // TODO: findNote()
                    break;
                case (4):
                    // TODO: deleteNote()
                    break;
            }
        } while (option != 0);
    }
}