-- Schema for CLI Agenda (simplified for testing without FK constraints)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS events;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(500),
    eventDate DATETIME,
    recurrent TINYINT,
    annualRecurring TINYINT,
    recurrenceInterval INT
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    body VARCHAR(100),
    event_fk INT,
    completed TINYINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    task_fk INT
);

-- Sample data for testing
INSERT INTO events (id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval) 
VALUES (1, 'Evento 1', 'Descripcion evento 1', '2025-06-15 10:00:00', 0, 0, 0);
INSERT INTO events (id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval) 
VALUES (2, 'Evento 2', 'Descripcion evento 2', '2025-07-20 14:00:00', 1, 1, 0);

INSERT INTO tasks (id, body, event_fk, completed) VALUES (1, 'Tarea 1', 1, 0);
INSERT INTO tasks (id, body, event_fk, completed) VALUES (2, 'Tarea 2', 0, 0);
INSERT INTO tasks (id, body, event_fk, completed) VALUES (3, 'Tarea 3', 2, 1);

INSERT INTO notes (id, body, task_fk) VALUES (1, 'Nota para tarea 1', 1);
INSERT INTO notes (id, body, task_fk) VALUES (2, 'Nota adicional para tarea 1', 1);
INSERT INTO notes (id, body, task_fk) VALUES (3, 'Nota para tarea 3', 3);