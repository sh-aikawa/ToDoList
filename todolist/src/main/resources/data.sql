INSERT INTO users (account_name, account_id, password)
VALUES ('testuser', 'tester',  '$2a$10$k6w07rbzaFKTnw.ggQgjIe4hSRj5TkobJxhMs6W0yhR9.Ag6QVEQC');
INSERT INTO users (account_name, account_id, password)
VALUES ('testuser2', 'tester2', '$2a$10$k6w07rbzaFKTnw.ggQgjIe4hSRj5TkobJxhMs6W0yhR9.Ag6QVEQC');


INSERT INTO tasks(user_id,title, limit_date, description) VALUES
    (1,'test','2025-06-05','TODOリスト完成');
INSERT INTO tasks(user_id,title, limit_date, description) VALUES
    (2,'test','2025-06-05','TODOリスト完成');

INSERT INTO comets(user_id, content) VALUES
    (1, 'test comment');
