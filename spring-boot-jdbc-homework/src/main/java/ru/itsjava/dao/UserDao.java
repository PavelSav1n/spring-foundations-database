package ru.itsjava.dao;

import ru.itsjava.domain.User;

public interface UserDao {

    int count();

    void insert(User user);

    void updateById(User user, int id);

    void delete(User user);
}
