package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public void insert(User user) {
        if (user.getId() == 0L) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM users u", User.class).getResultList();
    }

    // Обращаю внимание, что поскольку тут u.pets это List<Pet> то необходимо использовать IS EMPTY, а не IS NULL
    @Override
    public List<User> findAllWithoutPets() {
        return entityManager.createQuery("SELECT u FROM users u WHERE u.pets IS EMPTY", User.class).getResultList();
    }

    @Override
    public List<User> findAllWithPets() {
        return entityManager.createQuery("SELECT DISTINCT u FROM users u JOIN FETCH u.pets", User.class).getResultList();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findById(id));
    }
}
