DROP TABLE IF EXISTS tasks;

DROP TABLE IF EXISTS comets;

DROP TABLE IF EXISTS chat;

DROP TABLE IF EXISTS users;


CREATE TABLE
    IF NOT EXISTS users (
        user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
        username TEXT NOT NULL,
        password TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    IF NOT EXISTS tasks (
        task_id INTEGER AUTO_INCREMENT PRIMARY KEY,
        user_id INTEGER NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
        title TEXT,
        limit_date DATE,
        description TEXT,
        checked BOOLEAN DEFAULT FALSE,
        importance TEXT
    );

CREATE TABLE
    IF NOT EXISTS comets (
        comet_id INTEGER AUTO_INCREMENT PRIMARY KEY,
        user_id INTEGER NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
        content TEXT,
        happy BOOLEAN DEFAULT FALSE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    IF NOT EXISTS chat (
        send_user_id INTEGER  NOT NULL,
        receive_user_id INTEGER NOT NULL,
        FOREIGN KEY (send_user_id) REFERENCES users (user_id) ON DELETE CASCADE,
        FOREIGN KEY (receive_user_id) REFERENCES users (user_id) ON DELETE CASCADE,
        chat_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
        content TEXT NOT NULL,
        send_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        read BOOLEAN DEFAULT FALSE
    );