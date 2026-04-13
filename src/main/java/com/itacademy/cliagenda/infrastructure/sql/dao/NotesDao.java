package com.itacademy.cliagenda.infrastructure.sql.dao;

import com.itacademy.cliagenda.note.model.Note;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NotesDao {

    private Properties props;

    public NotesDao() {
        loadProperties();
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
}