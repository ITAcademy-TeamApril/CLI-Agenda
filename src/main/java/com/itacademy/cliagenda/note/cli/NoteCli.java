package com.itacademy.cliagenda.note.cli;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class NoteCli {

    private final Scanner scanner = new Scanner(System.in);
    private final NotesService serviceNotes;
    private final TaskService serviceTask;


    public NoteCli(NotesService serviceNotes, TaskService taskService) {
        this.serviceNotes = serviceNotes;
        this.serviceTask = taskService;

    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< NOTES MENU >>");
            System.out.println("1 - Create note");
            System.out.println("2 - List notes");
            System.out.println("3 - Find note");
            System.out.println("4 - Update note");
            System.out.println("5 - Delete note");
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
                    updateNote();
                    break;
                case (5):
                    deleteNote();
                    break;
                default:
                    System.out.println("Incorrect input, try again.");
                    break;
            }
        } while (option != 0);
    }

    public void createNote() {
        List<Task> tasks = serviceTask.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available. You need to create a task first before adding a note.");
            return;
        }

        System.out.println("Available tasks:");
        for (Task task : tasks) {
            System.out.println("  ID: " + task.getId() + " - " + task.getBody());
        }

        System.out.println("Introduce \"task ID\" to link this note to:");
        int idTaskForThisNote = scanner.nextInt();
        scanner.nextLine();

        Task taskTemp = serviceTask.findTaskById(idTaskForThisNote);
        if (taskTemp == null) {
            System.out.println("Task not found. Note not created.");
            return;
        }

        System.out.println("Introduce note body:");
        String body = scanner.nextLine();

        Note note = serviceNotes.createNote(body, taskTemp);
        System.out.println("Note created with ID: " + note.getId() + " linked to task with ID #" + idTaskForThisNote);
    }

    public void listNotes() {
        List<Note> notes = serviceNotes.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("No notes found..");
            return;
        }
        for (Note note : notes)
            System.out.println("ID: " + note.getId()
                    + " | " + note.getBody());
    }

    public void findNote() {
        System.out.println("Introduce note ID to search it:");
        try{
        int id = scanner.nextInt();
        scanner.nextLine(); // limpieza del fuck buffer!
        Note note = serviceNotes.findNoteById(id);
        if (note == null) {
            System.out.println("Note not found..");
        } else {
            System.out.println("ID: " + note.getId());
            System.out.println("Body: " + note.getBody());
            //System.out.println("Created: " + note.getCreationDate());
        }
        } catch (Exception e) {
            System.out.println("Note not found. Try again.");
            scanner.nextLine();
        }
    }

    public void deleteNote() {
        try{
        Note note = null;
        int id = 0;
        do {
            System.out.println("Introduce note ID to delete it:");
            id = scanner.nextInt();
            scanner.nextLine();
            note = serviceNotes.findNoteById(id);
            if (note == null) {
                System.out.println("Note not found. Try again.");
            }
        } while (note == null);

        serviceNotes.deleteNoteById(id);
        System.out.println("Note with id " + id + " is correctly deleted");
        } catch (Exception e) {
            System.out.println("Task not found. Try again.");
            scanner.nextLine();
        }
    }

    public void updateNote() {
        System.out.println("Introduce note ID to update:");
        try{
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Note note = serviceNotes.findNoteById(id);
        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        System.out.println("Current note:");
        System.out.println("  ID: " + note.getId());
        System.out.println("  Body: " + note.getBody());
        System.out.println("  Task FK: " + note.getTask_fk());
        System.out.println();

        System.out.println("Do you want to modify the body? (Y/N):");
        String modifyBody = scanner.nextLine();
        if (modifyBody.equalsIgnoreCase("y")) {
            System.out.println("Introduce new body:");
            String newBody = scanner.nextLine();
            note.changeBody(newBody);
        }

        List<Task> tasks = serviceTask.getAllTasks();
        if (!tasks.isEmpty()) {
            System.out.println("Do you want to modify the task association? (Y/N):");
            String modifyTask = scanner.nextLine();
            if (modifyTask.equalsIgnoreCase("y")) {
                System.out.println("Available tasks:");
                for (Task task : tasks) {
                    System.out.println("  ID: " + task.getId() + " - " + task.getBody());
                }
                System.out.println("Introduce new task ID:");
                int newTaskId = scanner.nextInt();
                scanner.nextLine();
                Task newTask = serviceTask.findTaskById(newTaskId);
                if (newTask != null) {
                    note.setTask_fk(newTaskId);
                } else {
                    System.out.println("Task not found. Keeping original task association.");
                }
            }
        }


        serviceNotes.updateNote(note);
        System.out.println("Note updated successfully.");
        } catch (Exception e) {
            System.out.println("Task not found. Try again.");
            scanner.nextLine();
        }
    }
}