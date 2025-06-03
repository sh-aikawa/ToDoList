DROP TABLE IF EXISTS tasks; /* tasksを削除し初期化*/

CREATE TABLE tasks(
    task_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    title TEXT,
    limit_date DATE,
    description TEXT
);