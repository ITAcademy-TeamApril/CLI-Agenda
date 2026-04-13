package com.itacademy.cliagenda.infrastructure.sql.dao;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.task.model.Task;
import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class TestNotesDao {

    private static Connection connection;
    private static Process dockerProcess;
    private NotesDao notesDao;

    @BeforeAll
    static void setUpAll() throws Exception {
        startDockerMySQL();
        loadProperties();
        createSchema();
    }

    @BeforeEach
    void setUp() throws Exception {
        notesDao = new NotesDao();
    }

    @AfterAll
    static void tearDownAll() {
        closeConnection();
        stopDockerMySQL();
    }

    private static void startDockerMySQL() throws Exception {
        System.out.println("Starting MySQL container...");
        
        ProcessBuilder pb = new ProcessBuilder(
            "docker-compose", "up", "-d", "mysql"
        );
        pb.directory(new File(System.getProperty("user.dir")));
        pb.redirectErrorStream(true);
        
        dockerProcess = pb.start();
        
        String output = new String(dockerProcess.getInputStream().readAllBytes());
        System.out.println("Docker output: " + output);
        
        dockerProcess.waitFor();
        
        System.out.println("Waiting for MySQL to be ready...");
        Thread.sleep(15000);
        
        System.out.println("MySQL container started.");
    }

    private static void loadProperties() throws Exception {
        Properties props = new Properties();
        try (InputStream input = TestNotesDao.class.getClassLoader().getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar application.properties");
            }
            props.load(input);
        }

        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        int retries = 10;
        while (retries > 0) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to MySQL.");
                return;
            } catch (SQLException e) {
                retries--;
                if (retries == 0) throw e;
                System.out.println("Retrying connection... (" + retries + " attempts left)");
                Thread.sleep(3000);
            }
        }
    }

    private static void createSchema() throws Exception {
        System.out.println("Creating schema...");
        
        StringBuilder sql = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(TestNotesDao.class.getClassLoader().getResourceAsStream("schema.sql")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
        }

        try (Statement stmt = connection.createStatement()) {
            String[] statements = sql.toString().split(";");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    try {
                        stmt.execute(statement.trim());
                    } catch (SQLException e) {
                        System.err.println("Error executing: " + statement.trim() + " - " + e.getMessage());
                    }
                }
            }
        }
        
        System.out.println("Schema created successfully.");
    }

    private static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    private static void stopDockerMySQL() {
        if (dockerProcess != null) {
            System.out.println("Stopping MySQL container...");
            try {
                ProcessBuilder pb = new ProcessBuilder(
                    "docker-compose", "down"
                );
                pb.directory(new File(System.getProperty("user.dir")));
                Process p = pb.start();
                p.waitFor();
            } catch (Exception e) {
                System.err.println("Error stopping Docker: " + e.getMessage());
            }
        }
    }

    @Test
    void testFindAllReturnsNotes() {
        List<Note> notes = notesDao.findAll();
        
        assertNotNull(notes);
        assertTrue(notes.size() >= 3, "Should have at least 3 notes from schema");
    }

    @Test
    void testFindAllReturnsNoteWithCorrectData() {
        List<Note> notes = notesDao.findAll();
        
        Note firstNote = notes.stream()
            .filter(n -> n.getId() == 1)
            .findFirst()
            .orElse(null);
        
        assertNotNull(firstNote, "Should find note with id 1");
        assertEquals("Esta es la nota numero 1", firstNote.getBody());
    }

    @Test
    void testFindAllReturnsEmptyListWhenNoNotes() throws SQLException {
        deleteAllNotes();
        
        List<Note> notes = notesDao.findAll();
        
        assertNotNull(notes);
        assertTrue(notes.isEmpty(), "Should return empty list when no notes");
        
        insertSampleData();
    }

    @Test
    void testSaveInsertsNote() throws SQLException {
        int initialCount = getNotesCount();
        
        Note note = new Note(999, "Nota para test de inserción");
        note.setTask_fk(new Task(1, "Tarea de prueba", java.time.LocalDateTime.now()));
        notesDao.save(note);
        
        int finalCount = getNotesCount();
        
        assertEquals(initialCount + 1, finalCount, "Should have added one note");
        
        deleteNoteById(999);
    }

    @Test
    void testSavePersistsCorrectData() throws SQLException {
        Note note = new Note(888, "Nota con datos persistentes");
        note.setTask_fk(new Task(1, "Tarea de prueba", java.time.LocalDateTime.now()));
        notesDao.save(note);
        
        Note retrieved = getNoteById(888);
        
        assertNotNull(retrieved, "Should retrieve saved note");
        assertEquals(888, retrieved.getId());
        assertEquals("Nota con datos persistentes", retrieved.getBody());
        
        deleteNoteById(888);
    }

    private int getNotesCount() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM notes")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private void deleteNoteById(int id) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM notes WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private void deleteAllNotes() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM notes");
        }
    }

    private Note getNoteById(int id) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT id, body, creation_date, last_update_date, task_fk FROM notes WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Note(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
                        rs.getTimestamp("last_update_date") != null ? rs.getTimestamp("last_update_date").toLocalDateTime() : null,
                        rs.getInt("task_fk")
                    );
                }
            }
        }
        return null;
    }

    private void insertSampleData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO notes (id, body, creation_date, task_fk) VALUES (1, 'Esta es la nota numero 1', NOW(), 1)");
            stmt.execute("INSERT INTO notes (id, body, creation_date, task_fk) VALUES (2, 'Esta es la nota numero 2', NOW(), 1)");
            stmt.execute("INSERT INTO notes (id, body, creation_date, task_fk) VALUES (3, 'Esta es la nota numero 3', NOW(), 2)");
        }
    }
}