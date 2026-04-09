package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;

import java.util.ArrayList;
import java.util.List;

public class NotesService {

    private NotesRepository repo;

    public NotesService() {
        this.repo = new NotesRepository();
        //TODO:
        // - Leer la base de datos
        // - Guardar cada línea en una instancia de Note
        // - Guardar cada note en el repo
        // - Crear métodos para añadir notes y gestionarlas
    }
}
