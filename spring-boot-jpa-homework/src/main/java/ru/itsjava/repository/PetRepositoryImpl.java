package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Pet;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PetRepositoryImpl implements PetRepository {
    private final EntityManager entityManager;

    @Override
    public void insert(Pet pet) {
        if (pet.getId() == 0) {
            entityManager.persist(pet);
        } else {
            entityManager.merge(pet);
        }
    }

    @Override
    public Pet findById(long id) {
        return entityManager.find(Pet.class, id);
    }

    @Override
    public List<Pet> findAll() {
        return entityManager.createQuery("SELECT p FROM pets p", Pet.class).getResultList();
    }

    @Override
    public void update(Pet pet) {
        entityManager.merge(pet);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findById(id));
    }
}
