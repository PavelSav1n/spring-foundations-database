package ru.itsjava.dao;

import ru.itsjava.domain.Pet;

import java.util.List;

public interface PetDao {

    int count();

    List<Pet> findAll();

    Pet insert(Pet pet);

    void updateById(Pet pet, long id);

    Pet delete(long id);

    Pet findById(long id);
}
