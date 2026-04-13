package com.itacademy.cliagenda.event.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private final int idEvent;
    private String description;
    private String title;
    private LocalDateTime dateTimeEvent;
    private boolean recurring;
    private List<Integer> taskIds;

    // constructor completo
    public Event(int idEvent, String description, String title, LocalDateTime dateTimeEvent, boolean recurring, List<Integer> taskIds) {
        this.idEvent = idEvent;
        this.description = description;
        this.title = title;
        this.dateTimeEvent = dateTimeEvent;
        this.recurring = recurring;
        this.taskIds = taskIds;
    }

    //constructor sin taskIds list
    public Event(int idEvent, String description, String title, LocalDateTime dateTimeEvent, boolean recurring) {
        this.idEvent = idEvent;
        this.description = description;
        this.title = title;
        this.dateTimeEvent = dateTimeEvent;
        this.recurring = recurring;
        this.taskIds = new ArrayList<>();
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

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void setTaskIds(List<Integer> taskIds) {
        this.taskIds = taskIds;
    }

    public void changeTitle(String title) {
        try {
            if (title == null) throw new  IllegalArgumentException("Title can't be null");
            if (title.length() >= 100) throw new IllegalArgumentException("Title must be shorter than 100 characters");
            this.title = title;
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void changeDescription(String description) {
        try {
            if (description != null && description.length() >= 500) throw new IllegalArgumentException("Description must be shorter than 500 characters");
            this.description = description;
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
