-- Schema for CLI Agenda (simplified for testing without FK constraints)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS events;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(250),
    eventDate DATETIME,
    recurrent TINYINT
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    body VARCHAR(100),
    event_fk INT
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    task_fk INT
);

-- Sample data for testing
INSERT INTO events (id, title, description, eventDate, recurrent) VALUES (1, 'Evento 1', 'Descripcion evento 1', NOW(), 0);
INSERT INTO events (id, title, description, eventDate, recurrent) VALUES (2, 'Evento 2', 'Descripcion evento 2', NOW(), 1);

INSERT INTO tasks (id, body, event_fk) VALUES (1, 'Tarea 1', 1);
INSERT INTO tasks (id, body, event_fk) VALUES (2, 'Tarea 2', 2);

INSERT INTO notes (id, body, task_fk) VALUES (1, 'Esta es la nota numero 1', 1);
INSERT INTO notes (id, body, task_fk) VALUES (2, 'Esta es la nota numero 2', 1);
INSERT INTO notes (id, body, task_fk) VALUES (3, 'Esta es la nota numero 3', 2);