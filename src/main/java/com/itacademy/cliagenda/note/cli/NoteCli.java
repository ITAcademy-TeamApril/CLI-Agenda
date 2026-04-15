package com.itacademy.cliagenda.note.cli;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.service.NotesService;

import java.util.List;
import java.util.Scanner;

public class NoteCli {

    private final Scanner scanner = new Scanner(System.in);
    private final NotesService service;


    public NoteCli(NotesService service) {
        this.service = service;
    }

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
                    createNote();
                    break;
                case (2):
                    listNotes();
                    break;
                case (3):
                    findNote();
                    break;
                case (4):
                    deleteNote();
                    break;
            }
        } while (option != 0);
    }

    public void createNote() {
        System.out.println("Introduce note body:");
        String body = scanner.nextLine();
        Note note = service.createNote(body);
        System.out.println("Note created with ID: " + note.getId());
    }

    public void listNotes() {
        List<Note> notes = service.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("No notes found..");
            return;
        }
        for (Note note : notes)
            System.out.println("ID: " + note.getId()
                    + " | " + note.getBody()
                    + " | " + note.getCreationDate());
    }

    public void findNote() {
        System.out.println("Introduce note ID to search it:");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpieza del fuck buffer!
        Note note = service.findNoteById(id);
        if (note == null) {
            System.out.println("Note not found..");
        } else {
            System.out.println("ID: " + note.getId());
            System.out.println("Body: " + note.getBody());
            //System.out.println("Created: " + note.getCreationDate());
        }

    }

    public void deleteNote() {
        System.out.println("Introduce note ID to delete it");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpieza del mamahuevo buffer!
        service.deleteNoteById(id);
        System.out.println("Note with id " + id + " is correctly deleted");
    }
}