/* Проверять:
1. Порядок заполнения/создания таблиц
2. Количество полей, соответствие имён
3. Название таблиц, к которым подключаемся (spring.datasource.url=jdbc:h2:mem:???)
4. Название сущности в Hibernate (@Entity(name = ?)) и в таблицах SQL
5. Связку REFERENCES и поля, по которым связываем поля в сущностях */

INSERT INTO users(name, age) VALUES
    ('Peter Parker', 25),
    ('Batman', 41),
    ('Martie McFly', 33),
    ('Emmet Brown', 55);

INSERT INTO pets (species, user_id) VALUES
    ('dog', 1),
    ('cat', 1),
    ('mouse', 2),
    ('pig', 3);