package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestNotesRepository {

    private NotesRepository repository;
    private Note note1;
    private Note note2;
    private Note note3;

    @BeforeEach
    void setUp() {
        note1 = new Note(1, "Nota 1");
        note2 = new Note(2, "Nota 2");
        note3 = new Note(3, "Nota 3");
    }

    @Test
    void testConstructor() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);
        repository = new NotesRepository(notes);
        assertEquals(2, repository.getNotes().size());
    }

    @Test
    void testGetNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        repository = new NotesRepository(notes);
        assertNotNull(repository.getNotes());
        assertEquals(1, repository.getNotes().size());
    }

    @Test
    void testAddNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        repository = new NotesRepository(notes);
        
        List<Note> moreNotes = new ArrayList<>();
        moreNotes.add(note2);
        moreNotes.add(note3);
        repository.addNotes(moreNotes);
        
        assertEquals(3, repository.getNotes().size());
    }

    @Test
    void testAddIndividualNote() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        repository = new NotesRepository(notes);
        
        repository.addIndividualNote(note2);
        
        assertEquals(2, repository.getNotes().size());
    }

    @Test
    void testAddIndividualNoteNull() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        repository = new NotesRepository(notes);
        
        repository.addIndividualNote(null);
        
        assertEquals(1, repository.getNotes().size());
    }

    @Test
    void testRemoveLastNote() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);
        notes.add(note3);
        repository = new NotesRepository(notes);
        
        repository.removeLastNote();
        
        assertEquals(2, repository.getNotes().size());
        assertEquals(note2, repository.getNotes().get(1));
    }

    @Test
    void testRemoveLastNoteEmptyList() {
        List<Note> notes = new ArrayList<>();
        repository = new NotesRepository(notes);
        
        repository.removeLastNote();
        
        assertEquals(0, repository.getNotes().size());
    }

    @Test
    void testRemoveNoteById() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);
        notes.add(note3);
        repository = new NotesRepository(notes);
        
        repository.removeNoteById(2);
        
        assertEquals(2, repository.getNotes().size());
        assertFalse(repository.getNotes().contains(note2));
    }

    @Test
    void testRemoveNoteByIdNotFound() {
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);
        repository = new NotesRepository(notes);
        
        repository.removeNoteById(99);
        
        assertEquals(2, repository.getNotes().size());
    }
}