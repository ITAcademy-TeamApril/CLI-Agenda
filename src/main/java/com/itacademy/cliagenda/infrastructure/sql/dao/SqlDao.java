package com.itacademy.cliagenda.infrastructure.sql.dao;

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

    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, creation_date, last_update_date, task_fk FROM notes";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
                LocalDateTime lastUpdateDate = rs.getTimestamp("last_update_date") != null
                    ? rs.getTimestamp("last_update_date").toLocalDateTime()
                    : null;
                int task_fk = rs.getInt("task_fk");

                notes.add(new Note(id, body, creationDate, lastUpdateDate, task_fk));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer notas de la base de datos: " + e.getMessage());
        }
        return notes;
    }

    public void save(Note note) {
        String query = "INSERT INTO notes (id, body, creation_date, last_update_date, task_fk) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, note.getId());
            pstmt.setString(2, note.getBody());
            pstmt.setTimestamp(3, Timestamp.valueOf(note.getCreationDate().replace("T", " ")));
            pstmt.setTimestamp(4, note.getLastUpdateDate() != null ? Timestamp.valueOf(note.getLastUpdateDate()) : null);
            pstmt.setInt(5, note.getTask_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar nota en la base de datos: " + e.getMessage());
        }
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, name, task_date, creation_date, last_update_date, event_fk FROM tasks";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                LocalDateTime taskDate = rs.getTimestamp("task_date") != null
                    ? rs.getTimestamp("task_date").toLocalDateTime()
                    : null;
                LocalDateTime creationDate = rs.getTimestamp("creation_date") != null
                    ? rs.getTimestamp("creation_date").toLocalDateTime()
                    : null;
                LocalDateTime lastUpdateDate = rs.getTimestamp("last_update_date") != null
                    ? rs.getTimestamp("last_update_date").toLocalDateTime()
                    : null;
                int event_fk = rs.getInt("event_fk");

                tasks.add(new Task(id, name, taskDate, creationDate, lastUpdateDate, event_fk));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer tareas de la base de datos: " + e.getMessage());
        }
        return tasks;
    }

    public void saveTask(Task task) {
        String query = "INSERT INTO tasks (id, name, task_date, creation_date, last_update_date, event_fk) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, task.getId());
            pstmt.setString(2, task.getName());
            pstmt.setTimestamp(3, task.getCreationDate() != null ? Timestamp.valueOf(task.getCreationDate()) : null);
            pstmt.setTimestamp(4, task.getLastUpdateDate() != null ? Timestamp.valueOf(task.getLastUpdateDate()) : null);
            pstmt.setInt(5, task.getEvent_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar tarea en la base de datos: " + e.getMessage());
        }
    }
}