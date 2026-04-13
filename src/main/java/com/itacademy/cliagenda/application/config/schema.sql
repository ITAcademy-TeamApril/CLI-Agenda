-- Schema for CLI Agenda

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    task_date DATETIME,
    creation_date DATETIME,
    last_update_date DATETIME,
    event_fk INT,
    FOREIGN KEY (event_fk) REFERENCES event(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    body VARCHAR(250),
    creation_date DATETIME,
    last_update_date DATETIME,
    task_fk INT,
    FOREIGN KEY (task_fk) REFERENCES tasks(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    description VARCHAR(250),
    creation_date DATETIME,
    last_update_date DATETIME,
    recurrent TINYINT,
    task_fk INT,
    FOREIGN KEY (task_fk) REFERENCES tasks(id) ON DELETE CASCADE
    );