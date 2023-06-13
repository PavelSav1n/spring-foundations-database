package ru.itsjava.domain.repository;

import ru.itsjava.domain.Genre;

public interface GenreRepository {

    Genre getByID(long id);

    void insert(Genre genre);

    void update(Genre genre);

    void delete(long id);
}
