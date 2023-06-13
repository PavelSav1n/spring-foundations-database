package ru.itsjava.repository;

import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;

import java.util.List;

public interface PetRepository {
    // CRUD
    void insert(Pet pet);

    Pet findByID(long id);

    List<Pet> findAll();

    void update(Pet pet);

    void delete(long id);
}
