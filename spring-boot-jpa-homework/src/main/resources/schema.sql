/* Проверять:
1. Порядок заполнения/создания таблиц
2. Количество полей, соответствие имён
3. Название таблиц, к которым подключаемся (spring.datasource.url=jdbc:h2:mem:???)
4. Название сущности в Hibernate (@Entity(name = ?)) и в таблицах SQL
5. Связку REFERENCES и поля, по которым связываем поля в сущностях */

DROP TABLE IF EXISTS pets, users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(256),
    age INT
);

CREATE TABLE pets (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    species VARCHAR(256),
    user_id BIGINT REFERENCES users(id)
);