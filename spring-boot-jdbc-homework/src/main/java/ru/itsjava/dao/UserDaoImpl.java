package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from users", Integer.class);
    }

    // Возвращаем список всех пользователей. Поскольку в БД могут быть пользователи без петов, то я объединил два запроса:
    // Первый возвращает таблицу (джойн users и pets) с юзерами у которых есть петы
    // Второй возвращает таблицу пользователей без петов и недостающие поля заполнены NULL
    @Override
    public List<User> findAll() {
        return jdbc.query("""
                SELECT u.id as UID, name AS user_name, age, pet_id, p.id AS PID, species FROM users u, pets p WHERE pet_id = p.id 
                UNION 
                SELECT id, name, age, pet_id, NULL, NULL FROM users where pet_id IS NULL;
                """, new UserMapper());
    }

    // Когда мы вставляем в H2 DB пользователя, мы не знаем, какой id нам присвоит база, поэтому метод insert будет возвращать нам пользователя с его id в базе дынных
    @Override
    public User insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // Есть два варианта пользователя, с петом или без:
        if (user.getPet() != null) { // если с петом, то инсертим с петом и используем конструктор с петом:
            Map<String, Object> params = Map.of("name", user.getName(), "age", user.getAge(), "pet_id", user.getPet().getId());
            jdbc.update("insert into users (name, age, pet_id) values (:name, :age, :pet_id)", new MapSqlParameterSource(params), keyHolder);
            return new User(keyHolder.getKey().longValue(), user.getName(), user.getAge(), user.getPet());
        }
        // если без пета, то инсертим без и используем конструктор без:
        Map<String, Object> params = Map.of("name", user.getName(), "age", user.getAge());
        jdbc.update("insert into users (name, age) values (:name, :age)", new MapSqlParameterSource(params), keyHolder);
        return new User(keyHolder.getKey().longValue(), user.getName(), user.getAge());

    }

    @Override
    public void updateById(User user, long id) {
        Map<String, Object> params = Map.of("id", id, "name", user.getName(), "age", user.getAge());
        jdbc.update("update users set name = :name, age = :age where id = :id", params);
    }

    @Override
    public void delete(User user) {
        Map<String, Object> params = Map.of("name", user.getName(), "age", user.getAge());
        jdbc.update("delete from users where name = :name and age = :age", params);
    }

    // Упростил логику, потому что объединил запросы и теперь получаю всех пользователей, независимо, есть петы или нет.
    // Сейчас UserMapper проверяет на нулевое значение поля pet_id. В зависимости от этого возвращаются пользователи с петом или без
    @Override
    public User findById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("""
                SELECT u.id as UID, name AS user_name, age, pet_id, p.id AS PID, species FROM users u, pets p WHERE u.id = :id and pet_id = p.id 
                UNION 
                SELECT id, name, age, pet_id, NULL, NULL FROM users where id = :id and pet_id IS NULL;
                """, params, new UserMapper());

    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs.getLong("PID") != 0) { // проверка на наличие пета
                return new User(rs.getLong("UID"), rs.getString("user_name"), rs.getInt("age"), new Pet(rs.getLong("PID"), rs.getString("species")));
            }
            return new User(rs.getLong("UID"), rs.getString("user_name"), rs.getInt("age"));
        }
    }

}
