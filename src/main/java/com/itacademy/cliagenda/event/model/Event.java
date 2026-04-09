package com.itacademy.cliagenda.event.model;

import java.time.LocalDateTime;
import java.util.List;

public class Event {

    private int idEvent;
    private String description;
    private String title;
    private LocalDateTime dateTimeEvent;
    private boolean recurring;
    private List<Integer> taskIds;

    public Event(int idEvent, String description, String title, LocalDateTime dateTimeEvent, boolean recurring) {
        this.idEvent = idEvent;
        this.description = description;
        this.title = title;
        this.dateTimeEvent = dateTimeEvent;
        this.recurring = recurring;
        this.taskIds = taskIds;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTimeEvent() {
        return dateTimeEvent;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public List<Integer> getTaskIds() {
        return taskIds;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTimeEvent(LocalDateTime dateTimeEvent) {
        this.dateTimeEvent = dateTimeEvent;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setTaskIds(List<Integer> taskIds) {
        this.taskIds = taskIds;
    }
}
