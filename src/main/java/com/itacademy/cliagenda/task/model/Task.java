package com.itacademy.cliagenda.task.model;

import com.itacademy.cliagenda.event.model.Event;

import java.time.LocalDateTime;

public class Task {
    private int Id;
    private String body;
    private int event_fk;

    public Task(int id, String body) {
        Id = id;
        this.body = body;
    }

    //Usar este constructor para cuando haya que recrear los objetos Task desde la base de datos.
    //o bien para cuando se creen con fk
    public Task(int id, String body, Event event) {
        Id = id;
        this.body = body;
        int event_fk=event.getId();
        if (event_fk!=0){
            this.event_fk = event_fk;
        }
    }

    public int getId() {
        return Id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body){
        try {
            if (body != null && body.length() > 250) {
                throw new IllegalArgumentException("La petición de escribir una nota no se ha realizado por ser excesivamente largo.");
            }
            this.body = body;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public int getEvent_fk() {
        return event_fk;
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
