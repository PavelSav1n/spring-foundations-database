package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

// Пример работы JdbcOperations без именованных параметров:
@Repository
@RequiredArgsConstructor
public class UserDaoImplOldVersion implements UserDao {
    private final JdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public User insert(User user) {
        jdbc.update("insert into users (name, age) values (?, ?)", user.getName(), user.getAge());
        return user;
    }

    @Override
    public void updateById(User user, long id) {
        jdbc.update("update users set name = ?, age = ? where id = ?", user.getName(), user.getAge(), id);
    }

    @Override
    public void delete(User user) {
        jdbc.update("delete from users where name = ? and age = ?", user.getName(), user.getAge());
    }

    @Override
    public User findUserById(long id) {
        // не нашёл конструктор, который принимал бы параметры в интерфейсе JdbcOperations.queryForObject, поэтому вот так:
        String query = "select id, name, age from users where id = " + id;
        return jdbc.queryForObject(query, new UserMapper());
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
        }
    }
}
