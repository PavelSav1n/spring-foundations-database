package ru.itsjava.service;

import ru.itsjava.domain.Film;

import java.util.Optional;

public interface FilmService {

    void printAllFilms();

    // crud

    Film create(Film film);

    Optional<Film> findById(long id);

}
