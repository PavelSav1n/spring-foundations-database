package ru.itsjava.repository;

import ru.itsjava.domain.Pet;

import java.util.List;

public interface PetRepository {
    // CRUD
    void insert(Pet pet);

    Pet findById(long id);

    List<Pet> findAll();

    void update(Pet pet);

    void delete(long id);
}
