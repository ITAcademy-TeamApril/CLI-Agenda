package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {
    private List<Note> Notes;

    public NotesRepository(List<Note> Notes) {
        this.Notes = new ArrayList<>();
        addNotes(Notes);
    }

    public NotesRepository() {
        this.Notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return Notes;
    }

    public void addNotes(List<Note> Notes) {
        if (Notes != null && !Notes.isEmpty()) {
            this.Notes.addAll(Notes);
        }
    }
    public void addIndividualNote(Note note) {
        if (note != null) {
            this.Notes.add(note);
        }
    }

    public void removeLastNote() {
        if (!Notes.isEmpty()) {
            Notes.remove(Notes.size() - 1);
        } else {
            System.out.println("No hay notas para eliminar.");
        }
    }

    public void removeNoteById(int id) {
        Note noteToRemove = null;
        for (Note note : Notes) {
            if (note.getId() == id) {
                noteToRemove = note;
                break;
            }
        }
        if (noteToRemove != null) {
            Notes.remove(noteToRemove);
        } else {
            System.out.println("No se encontró ninguna nota con el ID: " + id);
        }
    }
}
