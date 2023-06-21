package ru.itsjava.services;

import ru.itsjava.domain.User;

import java.util.List;

public interface UserService {
    // CRUD

    User create(User user);

    User findById(long id);

    List<User> findAll();

    void printAll();

    User update(User user);

    void delete(User user);
}
