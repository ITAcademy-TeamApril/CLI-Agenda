package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {
    private final List<Note> notes;

    public NotesRepository(List<Note> Notes) {
        this.notes = new ArrayList<>();
        addNotes(Notes);
    }

    public NotesRepository() {
        this.notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNotes(List<Note> Notes) {
        if (Notes != null && !Notes.isEmpty()) {
            this.notes.addAll(Notes);
        }
    }

    public void addIndividualNote(Note note) {
        if (note != null) {
            this.notes.add(note);
        }
    }

    public void removeNoteById(int id) {
        Note noteToRemove = null;
        for (Note note : notes) {
            if (note.getId() == id) {
                noteToRemove = note;
                break;
            }
        }
        if (noteToRemove != null) {
            notes.remove(noteToRemove);
        } else {
            System.out.println("No se encontró ninguna nota con el ID: " + id);
        }
    }

    public Note getNoteById(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    public List<Note> getNotesByTaskFK(int task_fk) {
        List<Note> result = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTask_fk() == task_fk) {
                result.add(note);
            }
        }
        return result;
    }
}
