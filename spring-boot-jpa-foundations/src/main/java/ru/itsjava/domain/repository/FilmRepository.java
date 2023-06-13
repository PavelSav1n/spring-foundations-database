package ru.itsjava.domain.repository;

import ru.itsjava.domain.Film;

import java.util.List;

public interface FilmRepository {

    List<Film> findAll();
}
