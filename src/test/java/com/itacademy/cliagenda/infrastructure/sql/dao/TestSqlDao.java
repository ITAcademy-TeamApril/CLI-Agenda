package com.itacademy.cliagenda.infrastructure.sql.dao;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.testing.TestContainerManager;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestSqlDao {

    private SqlDao sqlDao;

    @BeforeAll
    static void setUpAll() throws Exception {
        TestContainerManager.ensureRunning();
    }

    @BeforeEach
    void setUp() throws Exception {
        TestContainerManager.clearAllTables();
        sqlDao = SqlDao.getInstance();
    }

    @Test
    void testFindAllNotesReturnsNotes() {
        List<Note> notes = sqlDao.findAllNotes();
        assertNotNull(notes);
    }

    @Test
    void testFindAllNotesReturnsEmptyListWhenNoNotes() throws SQLException {
        List<Note> notes = sqlDao.findAllNotes();
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void testSaveNotesInsertsNote() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        int initialCount = getNotesCount(conn);
        
        Note note = new Note(1001, "Nota para test de inserción", 0);
        sqlDao.saveNotes(note);
        
        int finalCount = getNotesCount(conn);
        assertEquals(initialCount + 1, finalCount, "Should have added one note");
        
        deleteNoteById(conn, 1001);
    }

    @Test
    void testSaveNotesPersistsCorrectData() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        Note note = new Note(1002, "Nota con datos persistentes", 0);
        sqlDao.saveNotes(note);
        
        Note retrieved = getNoteById(conn, 1002);
        
        assertNotNull(retrieved, "Should retrieve saved note");
        assertEquals(1002, retrieved.getId());
        assertEquals("Nota con datos persistentes", retrieved.getBody());
        
        deleteNoteById(conn, 1002);
    }

    @Test
    void testDeleteNote() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        Note note = new Note(2001, "Nota para borrar", 0);
        sqlDao.saveNotes(note);
        
        int initialCount = getNotesCount(conn);
        sqlDao.deleteNote(2001);
        
        int finalCount = getNotesCount(conn);
        assertEquals(initialCount - 1, finalCount);
    }

    @Test
    void testUpdateNote() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        Note note = new Note(2002, "Nota original", 0);
        sqlDao.saveNotes(note);
        
        note.changeBody("Nota actualizada");
        sqlDao.updateNote(note);
        
        String updatedBody = getNoteBodyFromDb(conn, 2002);
        assertEquals("Nota actualizada", updatedBody);
        
        deleteNoteById(conn, 2002);
    }

    @Test
    void testFindAllTasksReturnsTasks() throws SQLException {
        List<Task> tasks = sqlDao.findAllTasks();
        assertNotNull(tasks);
    }

    @Test
    void testSaveTaskInsertsTask() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        int initialCount = getTasksCount(conn);
        
        Task task = new Task(3001, "Tarea para test de inserción", 0);
        sqlDao.saveTask(task);
        
        int finalCount = getTasksCount(conn);
        assertEquals(initialCount + 1, finalCount);
        
        deleteTaskById(conn, 3001);
    }

    @Test
    void testDeleteTask() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        Task task = new Task(3002, "Tarea para borrar", 0);
        sqlDao.saveTask(task);
        
        int initialCount = getTasksCount(conn);
        sqlDao.deleteTask(3002);
        
        int finalCount = getTasksCount(conn);
        assertEquals(initialCount - 1, finalCount);
    }

    @Test
    void testUpdateTask() throws SQLException {
        Connection conn = TestContainerManager.getConnection();
        Task task = new Task(3003, "Tarea original", 0);
        sqlDao.saveTask(task);
        
        task.changeBody("Tarea actualizada");
        sqlDao.updateTask(task);
        
        String updatedBody = getTaskBodyFromDb(conn, 3003);
        assertEquals("Tarea actualizada", updatedBody);
        
        deleteTaskById(conn, 3003);
    }

    @Test
    void testFindAllEventsReturnsEvents() throws SQLException {
        List<com.itacademy.cliagenda.event.model.Event> events = sqlDao.findAllEvents();
        assertNotNull(events);
    }

    private int getNotesCount(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM notes")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private int getTasksCount(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private void deleteNoteById(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM notes WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private void deleteTaskById(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Note getNoteById(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT id, body, task_fk FROM notes WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Note(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getInt("task_fk")
                    );
                }
            }
        }
        return null;
    }

    private String getNoteBodyFromDb(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT body FROM notes WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("body");
                }
            }
        }
        return null;
    }

    private String getTaskBodyFromDb(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT body FROM tasks WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("body");
                }
            }
        }
        return null;
    }
}