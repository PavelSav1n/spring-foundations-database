package ru.itsjava.services;

import ru.itsjava.domain.User;

import java.util.List;

public interface UserService {

    int count();

    List<User> findAll();

    User insert(User user);

    void updateById(User user, long id);

    void delete(User user);

    User findById(long id);
}
