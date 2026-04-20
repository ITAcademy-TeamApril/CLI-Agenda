package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

public class NotesService {

    private final NotesRepository repo;
    private final SqlDao dao;

    public NotesService(NotesRepository repo) {
        this.repo = repo;
        this.dao = SqlDao.getInstance();
        List<Note> notesFromDb = dao.findAllNotes();
        repo.addNotes(notesFromDb);
    }

    public Note createNote(String body) {
        return createNote(body, null);
    }

    public Note createNote(String body, Task task_fk) {
        int id = generateNextId();
        int taskFk = task_fk != null ? task_fk.getId() : 0;
        Note newNote = new Note(id, body, taskFk);
        dao.saveNotes(newNote);
        repo.addIndividualNote(newNote);
        return newNote;
    }

    public List<Note> getAllNotes() {
        return repo.getNotes();
    }

    public Note findNoteById(int id) {
        return repo.getNoteById(id);
    }

    public void deleteNoteById(int id) {
        dao.deleteNote(id);
        repo.removeNoteById(id);
    }

    public void updateNote(Note note) {
        dao.updateNote(note);
        repo.removeNoteById(note.getId());
        repo.addIndividualNote(note);
    }

    public List<Note> getNotesByTaskId(int taskId) {
        return repo.getNotesByTaskFK(taskId);
    }


    private int generateNextId() {
        List<Note> notes = repo.getNotes();
        int maxId = 0;
        for (Note note : notes) {
            if (note.getId() > maxId) {
                maxId = note.getId();
            }
        }
        return maxId + 1;
    }
}