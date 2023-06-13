package ru.itsjava.repository;

import ru.itsjava.domain.User;

import java.util.List;

public interface UserRepository {
    // CRUD
    void insert(User user);

    User findByID(long id);

    List<User> findAll();

    List<User> findAllWithoutPets();

    List<User> findAllWithPets();

    void update(User user);

    void delete(long id);

}
