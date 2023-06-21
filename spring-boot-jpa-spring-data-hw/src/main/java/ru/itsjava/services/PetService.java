package ru.itsjava.services;

import ru.itsjava.domain.Pet;

import java.util.List;

public interface PetService {
    // CRUD

    Pet create(Pet pet);

    Pet findById(long id);

    List<Pet> findAll();

    void printAll();

    Pet update(Pet pet);

    void delete(Pet pet);
}
