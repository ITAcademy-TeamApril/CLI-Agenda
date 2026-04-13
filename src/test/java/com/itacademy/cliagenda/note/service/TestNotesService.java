package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.task.model.Task;
import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class TestNotesService {

    private static Connection connection;
    private static Process dockerProcess;
    private NotesService notesService;

    @BeforeAll
    static void setUpAll() throws Exception {
        startDockerMySQL();
        loadProperties();
        createSchema();
    }

    @BeforeEach
    void setUp() throws Exception {
        notesService = new NotesService();
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
        try (InputStream input = TestNotesService.class.getClassLoader().getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties")) {
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
                new InputStreamReader(TestNotesService.class.getClassLoader().getResourceAsStream("schema.sql")))) {
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
    void testExtractDatabaseNotesTable() {
        List<Note> notes = notesService.extractDatabaseNotesTable();
        
        assertNotNull(notes);
        assertTrue(notes.size() >= 3, "Should have at least 3 notes from schema");
    }

    @Test
    void testCheckNoteId() {
        int maxId = notesService.checkNoteId();
        
        assertTrue(maxId >= 3, "Max ID should be at least 3 from sample data");
    }

    @Test
    void testCreateNote() {
        Note note = notesService.createNote(100, "Nueva nota de prueba");
        
        assertNotNull(note);
        assertEquals(100, note.getId());
        assertEquals("Nueva nota de prueba", note.getBody());
    }

    @Test
    void testCreateNoteAllParams() {
        Timestamp creation = Timestamp.valueOf("2024-01-01 10:00:00");
        Timestamp lastUpdate = Timestamp.valueOf("2024-01-02 12:00:00");
        
        Note note = notesService.createNoteAllParams(1, "Nota completa", 
            creation.toLocalDateTime(), lastUpdate.toLocalDateTime(), 1);
        
        assertNotNull(note);
        assertEquals(1, note.getId());
        assertEquals("Nota completa", note.getBody());
        assertEquals(1, note.getTask_fk());
    }

    @Test
    void testAddNoteDB() throws SQLException {
        int initialCount = getNotesCount();
        
        Note note = notesService.createNote(999, "Nota para test de inserción");
        note.setTask_fk(new Task(1, "Tarea de prueba", java.time.LocalDateTime.now()));
        notesService.addNoteDB(note);
        
        int finalCount = getNotesCount();
        
        assertEquals(initialCount + 1, finalCount, "Should have added one note");
        
        deleteNoteById(999);
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
}
