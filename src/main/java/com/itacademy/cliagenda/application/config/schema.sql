-- Schema for CLI Agenda (simplified without FK constraints)

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(250),
    creation_date DATETIME,
    last_update_date DATETIME,
    recurrent TINYINT,
    task_fk INT
    );

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    task_date DATETIME,
    creation_date DATETIME,
    last_update_date DATETIME,
    event_fk INT
    );

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    creation_date DATETIME,
    last_update_date DATETIME,
    task_fk INT
    );

SET FOREIGN_KEY_CHECKS = 1;

-- Sample data
INSERT INTO events (id, title, description, creation_date, recurrent, task_fk) VALUES (1, 'Evento 1', 'Descripcion evento 1', NOW(), 0, 1);
INSERT INTO events (id, title, description, creation_date, recurrent, task_fk) VALUES (2, 'Evento 2', 'Descripcion evento 2', NOW(), 1, NULL);

INSERT INTO tasks (id, name, task_date, creation_date, event_fk) VALUES (1, 'Tarea 1', NOW(), NOW(), 1);
INSERT INTO tasks (id, name, task_date, creation_date, event_fk) VALUES (2, 'Tarea 2', NOW(), NOW(), 2);

INSERT INTO notes (id, body, creation_date, task_fk) VALUES (1, 'Esta es la nota numero 1', NOW(), 1);
INSERT INTO notes (id, body, creation_date, task_fk) VALUES (2, 'Esta es la nota numero 2', NOW(), 1);
INSERT INTO notes (id, body, creation_date, task_fk) VALUES (3, 'Esta es la nota numero 3', NOW(), 2);