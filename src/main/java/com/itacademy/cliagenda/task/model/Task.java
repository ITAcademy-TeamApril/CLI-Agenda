package com.itacademy.cliagenda.task.model;

import java.time.LocalDateTime;

public class Task {
    private int Id;
    private String name;
    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime lastUpdateDate;
    private int event_fk;

    public Task(int id, String name, LocalDateTime eventDate) {
        Id = id;
        this.name = name;
    }

    public Task(int id, String name, LocalDateTime eventDate, LocalDateTime creationDate, LocalDateTime lastUpdateDate, int event_fk) {
        Id = id;
        this.name = name;
        this.creationDate=creationDate;
        this.lastUpdateDate=lastUpdateDate;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getEvent_fk() {
        return event_fk;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setEvent_fk(Event event) {
        try{
            if (event.getId() < 0) {
                throw new IllegalArgumentException("El valor proporcionado para event_fk no es válido.");
            }
            this.event_fk = event.getId();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
