package com.itacademy.cliagenda.infrastructure.sql.dao;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.task.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlDao {

    private static final SqlDao INSTANCE = new SqlDao();
    private Properties props;

    //////////////////////////////// SQL Connection Methods

    private SqlDao() {
        loadProperties();
    }

    public static SqlDao getInstance() {
        return INSTANCE;
    }

    private void loadProperties() {
        this.props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar application.properties");
            }
            assert props != null;
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar properties", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("jdbc.url"),
            props.getProperty("jdbc.username"),
            props.getProperty("jdbc.password")
        );
    }

    ////////////////////////////////

    public List<Note> findAllNotes() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, task_fk FROM notes";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                int task_fk = rs.getInt("task_fk");

                notes.add(new Note(id, body, task_fk));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer notas de la base de datos: " + e.getMessage());
        }
        return notes;
    }

    public void saveNotes(Note note) {
        String query = "INSERT INTO notes (id, body, task_fk) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, note.getId());
            pstmt.setString(2, note.getBody());
            pstmt.setInt(5, note.getTask_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar nota en la base de datos: " + e.getMessage());
        }
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk FROM tasks";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                int event_fk = rs.getInt("event_fk");

                tasks.add(new Task(id, body, event_fk));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer tareas de la base de datos: " + e.getMessage());
        }
        return tasks;
    }

    public void saveTask(Task task) {
        String query = "INSERT INTO tasks (id, body, event_fk) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, task.getId());
            pstmt.setString(2, task.getBody());
            pstmt.setInt(5, task.getEvent_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar tarea en la base de datos: " + e.getMessage());
        }
    }

    public List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT id, title, description, eventDate, recurring FROM events";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime eventDate = rs.getTimestamp("eventDate").toLocalDateTime();
                boolean recurrent = rs.getBoolean("recurrent");

                events.add(new Event(id, title, description, eventDate, recurrent));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer notas de la base de datos: " + e.getMessage());
        }
        return events;
    }

    public void saveEvents(Event event) {
        String query = "INSERT INTO notes (id, title, description, eventDate, recurring) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, event.getId());
            pstmt.setString(2, event.getTitle());
            pstmt.setString(3, event.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(6, event.isRecurring());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar nota en la base de datos: " + e.getMessage());
        }
    }
}