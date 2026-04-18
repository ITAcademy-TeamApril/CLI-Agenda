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

    /// ///////////////////////////// SQL Connection Methods

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

    /// /////////////////////////////

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
            pstmt.setInt(3, note.getTask_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar nota en la base de datos: " + e.getMessage());
        }
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                int event_fk = rs.getInt("event_fk");
                boolean completed = rs.getBoolean("completed");

                tasks.add(new Task(id, body, event_fk, completed));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer tareas de la base de datos: " + e.getMessage());
        }
        return tasks;
    }

    public void saveTask(Task task) {
        String query = "INSERT INTO tasks (id, body, event_fk, completed) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, task.getId());
            pstmt.setString(2, task.getBody());
            pstmt.setInt(3, task.getEvent_fk());
            pstmt.setBoolean(4, task.isCompleted());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar tarea en la base de datos: " + e.getMessage());
        }
    }

    public List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT id, title, description, eventDate, recurrent FROM events";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime eventDate = rs.getTimestamp("eventDate").toLocalDateTime();
                boolean recurrent = rs.getBoolean("recurrent");
                boolean annualRecurring = rs.getBoolean("annualRecurring");
                int recurrenceInterval = rs.getInt("recurrenceInterval");

                events.add(new Event(id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer notas de la base de datos: " + e.getMessage());
        }
        return events;
    }

    public void saveEvents(Event event) {
        String query = "INSERT INTO events (id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, event.getId());
            pstmt.setString(2, event.getTitle());
            pstmt.setString(3, event.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(5, event.isRecurring());
            pstmt.setBoolean(6, event.isAnnualRecurring());
            pstmt.setInt(7, event.getRecurrenceInterval());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar evento en la base de datos: " + e.getMessage());
        }
    }

    public void deleteNote(int id) {
        String query = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar nota de la base de datos: " + e.getMessage());
        }
    }

    public void updateNote(Note note) {
        String query = "UPDATE notes SET body = ?, task_fk = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, note.getBody());
            pstmt.setInt(2, note.getTask_fk());
            pstmt.setInt(3, note.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar nota en la base de datos: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar tarea de la base de datos: " + e.getMessage());
        }
    }

    public void updateTask(Task task) {
        String query = "UPDATE tasks SET body = ?, event_fk = ?, completed = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, task.getBody());
            pstmt.setInt(2, task.getEvent_fk());
            pstmt.setBoolean(3, task.isCompleted());
            pstmt.setInt(4, task.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar tarea en la base de datos: " + e.getMessage());
        }
    }

    public void deleteEvent(int id) {
        String query = "DELETE FROM events WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar evento de la base de datos: " + e.getMessage());
        }
    }

    public void updateEvent(Event event) {
        String query = "UPDATE events SET title = ?, description = ?, eventDate = ?, recurrent = ?, annualRecurring = ?, recurrenceInterval = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setTimestamp(3, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(4, event.isRecurring());
            pstmt.setBoolean(5, event.isAnnualRecurring());
            pstmt.setInt(6, event.getRecurrenceInterval());
            pstmt.setInt(7, event.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar evento en la base de datos: " + e.getMessage());
        }
    }
}