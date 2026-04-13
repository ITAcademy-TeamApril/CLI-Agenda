package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.infrastructure.sql.dao.SqlDao;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class NotesService {

    private NotesRepository repo;
    private SqlDao dao;

    public NotesService() {
        this.repo = new NotesRepository();
        this.dao = SqlDao.getInstance();
        repo.addNotes(dao.findAll());
    }

    public List<Note> extractDatabaseNotesTable(){
        return dao.findAll();
    }

    Note createNoteAllParams(int id, String body, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int task_fk){
        return new Note(id, body, creationDate, lastUpdateDate, task_fk);
    }

    Note createNote(int id, String body){
        return new Note(id, body);
    }

    int checkNoteId(){
        List<Note> notes = repo.getNotes();
        if (notes.isEmpty()) {
            return 0;
        }
        int maxId = 0;
        for (Note note : notes) {
            if (note.getId() > maxId) {
                maxId = note.getId();
            }
        }
        return maxId;
    }

    public void addNote(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el contenido de la nota:");
        String body = scanner.nextLine();
        
        int id = checkNoteId() + 1;
        Note note = createNote(id, body);
        
        addNoteDB(note);
        repo.addIndividualNote(note);
        System.out.println("Nota añadida con ID: " + id);
    }

    public void addNoteDB(Note note){
        dao.save(note);
    }

}
