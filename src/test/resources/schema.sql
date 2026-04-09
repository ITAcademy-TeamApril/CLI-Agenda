-- Schema for CLI Agenda

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS events;

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    event_date DATETIME,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_update_date DATETIME
);

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_update_date DATETIME,
    event_fk INT,
    FOREIGN KEY (event_fk) REFERENCES events(id) ON DELETE CASCADE
);

-- Sample data for testing
INSERT INTO events (id, name, event_date) VALUES 
(1, 'Reunión de equipo', '2024-06-15 10:00:00'),
(2, 'Presentación proyecto', '2024-06-20 14:00:00'),
(3, 'Entrega deadline', '2024-06-30 23:59:00');

INSERT INTO notes (id, body, event_fk) VALUES 
(1, 'Preparar slides para la reunión', 1),
(2, 'Revisar presupuesto', 1),
(3, 'Practicar presentación', 2);
