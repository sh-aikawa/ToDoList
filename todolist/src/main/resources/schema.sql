DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users; 


CREATE TABLE users(
    user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tasks(
    task_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    title TEXT,
    limit_date DATE,
    description TEXT,
    checked BOOLEAN DEFAULT FALSE
);
