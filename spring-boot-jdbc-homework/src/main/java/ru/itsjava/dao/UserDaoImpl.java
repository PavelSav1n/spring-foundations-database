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
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from users", Integer.class);
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

    // Ищем пользователя по id. Поскольку мы не знаем, есть ли пет у этого пользователя, используем запрос только к таблице users.
    // Маппер смотрит, непустое ли поле pet_id и в зависимости от этого возвращает нам пользователя без пета или с подставным петом, чтобы воспользоваться другим маппером
    // Дальше если у этого пользователя есть пет, используем запрос с джойном таблиц и маппер для пользователей с петами и возвращаем полноценного пользователя.
    // Если нет, возвращаем безпетового пользователя.
    @Override
    public User findUserById(long id) {
        Map<String, Object> params = Map.of("id", id);
        User userWithoutPet = jdbc.queryForObject("select id, name, age, pet_id from users where id = :id", params, new UserMapper());
        if (userWithoutPet.getPet() != null) {
            return jdbc.queryForObject("select u.id as UID, u.name as user_name, age, p.id as PID, species from users u, pets p where u.id = :id and pet_id = p.id", params, new UserMapperWithPets());
        }
        return userWithoutPet;
    }


    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs.getLong("pet_id") == 0) {
                return new User(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
            }
            return new User(rs.getLong("id"), rs.getString("name"), rs.getInt("age"), new Pet(rs.getLong("pet_id"), "FOUND_PET"));
        }
    }

    private static class UserMapperWithPets implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("UID"), rs.getString("user_name"), rs.getInt("age"), new Pet(rs.getLong("PID"), rs.getString("species")));
        }
    }

}
