package ru.itsjava.dao;

import ru.itsjava.domain.User;

public interface UserDao {

    int count();

    User insert(User user);

    void updateById(User user, long id);

    void delete(User user);

    User findUserById(long id);

}
