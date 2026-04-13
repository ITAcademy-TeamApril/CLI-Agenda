-- Schema for CLI Agenda

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