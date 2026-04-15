-- Create user and grant permissions
CREATE USER IF NOT EXISTS 'TeamApril'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON agenda_db.* TO 'TeamApril'@'%';
FLUSH PRIVILEGES;

-- Schema for CLI Agenda

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS events;

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

-- Sample data for testing
INSERT INTO events (id, title, description, event_date) VALUES
(1, 'Reunión de equipo', 'descripcion', '2024-06-15 10:00:00'),
(2, 'Presentación proyecto','descripcion' , '2024-06-20 14:00:00'),
(3, 'Entrega deadline', 'descripcion' ,'2024-06-30 23:59:00');

INSERT INTO notes (id, body, event_fk) VALUES 
(1, 'Preparar slides para la reunión', 1),
(2, 'Revisar presupuesto', 1),
(3, 'Practicar presentación', 2);
