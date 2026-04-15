package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.task.model.Task;

public class Note {
    private final int id;
    private String body;
    private int task_fk;

    //Usar este constructor para crear nuevas Notas.
    public Note(int id, String body, int task_fk) {
        this.id = id;
        changeBody(body);
        this.task_fk = task_fk;
    }

    //Usar este constructor para cuando haya que recrear los objetos Notas desde la base de datos.
    //o bien para cuando se creen con fk

    //crear método extraiga id de task y cree la nota con ese ide fk
    public Note(int id, String body, Task task) {
        this.id = id;
        this.body = body;
        int task_fk = task.getId();
        if (task_fk != 0) {
            this.task_fk = task_fk;
        }
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body) {
        try {
            if (body != null && body.length() > 250) {
                throw new IllegalArgumentException("La petición de escribir una nota no se ha realizado por ser excesivamente largo.");
            }
            this.body = body;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public int getTask_fk() {
        return task_fk;
    }

    public void setTask_fk(Task task) {
        try {
            if (task.getId() < 0) {
                throw new IllegalArgumentException("El valor proporcionado para event_fk no es válido.");
            }
            this.task_fk = task.getId();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
