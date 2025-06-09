INSERT INTO users (username, password)
VALUES ('testuser', '$2a$10$k6w07rbzaFKTnw.ggQgjIe4hSRj5TkobJxhMs6W0yhR9.Ag6QVEQC');


INSERT INTO tasks(user_id,title, limit_date, description) VALUES
    (1,'test','2025-06-05','TODOリスト完成');
INSERT INTO tasks(user_id,title, limit_date, description) VALUES
    (1,'test','2025-06-05','TODOリスト完成');

INSERT INTO comets(user_id, content) VALUES
    (1, 'test comment');
