package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private List<Task> tasks;

    public TaskRepository(List<Task> tasks) {
        this.notes = new ArrayList<>();
        addNotes(Notes);
    }

    public NotesRepository() {
        this.notes = new ArrayList<>();
    }
}
