package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Genre;

@Repository
@RequiredArgsConstructor
@Transactional // хз зачем это, но без неё ругается на небезопасный persist
public class GenreRepositoryImpl implements GenreRepository {
    private final EntityManager entityManager;

    @Override
    public Genre getById(long id) {
        return entityManager.find(Genre.class, id);
    }

    @Override
    public void insert(Genre genre) {
        if (genre.getId() == 0L) { // если id не существует, значит сущность ещё не находится в контексте
            entityManager.persist(genre); // передаём сущность в контекст EntityManager
        } else {
            entityManager.merge(genre); // в противном случае сущность есть в контексте, обновляем сущность в контексте EntityManager
        }

    }

    @Override
    public void update(Genre genre) {
        entityManager.merge(genre);
    }

    @Override
    public void delete(long id) {
        Genre genreToBeDeleted = entityManager.find(Genre.class, id);
        entityManager.remove(genreToBeDeleted);
    }
}
