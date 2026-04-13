package com.itacademy.cliagenda.note.model;

import java.time.LocalDateTime;

public class Task {
    private int Id;
    private String name;
    private LocalDateTime eventDate;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime lastUpdateDate;

    public Task(int id, String name, LocalDateTime eventDate) {
        Id = id;
        this.name = name;
        this.eventDate=eventDate;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

}
