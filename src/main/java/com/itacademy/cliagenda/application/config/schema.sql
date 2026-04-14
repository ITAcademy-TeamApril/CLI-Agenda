SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(250),
    eventDate DATETIME,
    recurrent TINYINT,
    );

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    body VARCHAR(100),
    event_fk INT,
    FOREIGN KEY (event_fk) REFERENCES events(id)
    );

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    task_fk INT,
    FOREIGN KEY (task_fk) REFERENCES tasks(id)
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