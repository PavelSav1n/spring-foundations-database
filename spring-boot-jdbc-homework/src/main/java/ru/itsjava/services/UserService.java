package ru.itsjava.services;

import ru.itsjava.domain.User;

public interface UserService {

    int count();

    void insert(User user);

    void updateById(User user, long id);

    void delete(User user);

    User findUserById(long id);
}
