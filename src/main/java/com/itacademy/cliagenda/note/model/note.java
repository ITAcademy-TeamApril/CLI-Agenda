package com.itacademy.cliagenda.note.model;

import java.time.LocalDateTime;

public class note {
    private final int id;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime lastUpdateDate;
    private String body;

    public note(int id, String body) {
        this.id = id;
        changeBody(body);
    }

    public int getId() {
        return id;
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
            this.lastUpdateDate = LocalDateTime.now();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
}
