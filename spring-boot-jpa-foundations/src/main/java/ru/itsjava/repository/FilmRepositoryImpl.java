package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Film;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmRepositoryImpl implements FilmRepository {

    private final EntityManager entityManager;

    @Override
    public List<Film> findAll() {
        return entityManager.createQuery("SELECT DISTINCT f FROM films f JOIN FETCH f.genre JOIN FETCH f.places ", Film.class).getResultList();
//                                                         ^     ^        ^         ^        ^                  ^
//                                                    remove   all     entity    to fetch   connected fields with
//                                                 repeating  data      name    lazy-type       other entities
//                                                      rows                      fields
    }
}
