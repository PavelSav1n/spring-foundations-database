package ru.itsjava.repository;

import ru.itsjava.domain.Genre;

public interface GenreRepository {

    Genre getById(long id);

    void insert(Genre genre);

    void update(Genre genre);

    void delete(long id);
}
