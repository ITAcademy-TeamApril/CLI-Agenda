package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.task.model.Task;


import java.util.List;

public class NotesService {

    private final NotesRepository repo;
    private SqlDao dao;

    public NotesService(NotesRepository repo) {
        this.repo = repo;
        //this.dao = SqlDao.getInstance();
        //repo.addNotes(dao.findAll());
    }

    public Note createNote(String body, Task task_fk) {
        int id = generateNextId();
        Note newNote = new Note(id, body, task_fk);
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
        repo.removeNoteById(id);
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



    /*
    public void addNoteDB(Note note){
        dao.saveNotes(note);
    }
    */

    /*
    public List<Note> extractDatabaseNotesTable(){
        return dao.findAll();
    }
    */

    /*
    Note createNoteAllParams(int id, String body, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int task_fk){
        return new Note(id, body, creationDate, lastUpdateDate, task_fk);
    }
    */

}
