package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.User;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public void insert(User user) {
        jdbc.update("insert into users (name, age) values (?, ?)", user.getName(), user.getAge());
    }

    @Override
    public void updateById(User user, int id) {
        jdbc.update("update users set name = ?, age = ? where id = ?", user.getName(), user.getAge(), id);
    }

    @Override
    public void delete(User user) {
        jdbc.update("delete from users where name = ? and age = ?", user.getName(), user.getAge());
    }
}
