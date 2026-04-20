package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestNotesService {

    private NotesService notesService;
    private NotesRepository repo;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        repo = new NotesRepository();
        notesService = new NotesService(repo);
    }

    @Test
    void testGetAllNotesEmpty() {
        List<Note> notes = notesService.getAllNotes();
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void testCreateNote() {
        Note note = notesService.createNote("Nueva nota de prueba");
        
        assertNotNull(note);
        assertEquals("Nueva nota de prueba", note.getBody());
        
        List<Note> notes = notesService.getAllNotes();
        assertEquals(1, notes.size());
    }

    @Test
    void testDeleteNoteById() {
        Note note = notesService.createNote("Nota para borrar");
        
        notesService.deleteNoteById(note.getId());
        
        assertNull(notesService.findNoteById(note.getId()));
    }

    @Test
    void testUpdateNote() {
        Note note = notesService.createNote("Nota original");
        
        note.changeBody("Nota actualizada");
        notesService.updateNote(note);
        
        Note updated = notesService.findNoteById(note.getId());
        assertNotNull(updated);
        assertEquals("Nota actualizada", updated.getBody());
    }

    @Test
    void testFindNoteById() {
        Note note = notesService.createNote("Nota buscada");
        
        Note found = notesService.findNoteById(note.getId());
        
        assertNotNull(found);
        assertEquals("Nota buscada", found.getBody());
    }

    @Test
    void testFindNoteByIdNotFound() {
        Note note = notesService.findNoteById(999);
        assertNull(note);
    }

    @Test
    void testCreateNoteWithTask() {
        Note note = notesService.createNote("Nota con tarea", null);
        
        assertNotNull(note);
        assertEquals("Nota con tarea", note.getBody());
    }

    @Test
    void testGetNotesByTaskId() {
        Note note1 = notesService.createNote("Nota 1", null);
        Note note2 = notesService.createNote("Nota 2", null);
        
        List<Note> notesWithNoTask = notesService.getNotesByTaskId(0);
        assertEquals(2, notesWithNoTask.size());
    }
}