package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class NotesService {

    private NotesRepository repo;
    private Properties props;

    public NotesService() {
        this.repo = new NotesRepository();
        loadProperties();
        repo.addNotes(extractDatabaseNotesTable());
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

    public List<Note> extractDatabaseNotesTable(){
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
                
                notes.add(createNoteAllParams(id, body, creationDate, lastUpdateDate, task_fk));
            }
        } catch (SQLException e) {
            System.err.println("Error al extraer notas de la base de datos: " + e.getMessage());
        }
        return notes;
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
