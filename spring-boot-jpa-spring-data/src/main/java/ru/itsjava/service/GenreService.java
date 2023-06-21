package ru.itsjava.service;

import ru.itsjava.domain.Genre;

public interface GenreService {

    void changeGenreByName(String oldName, String newName);

    void printGenreByName(String name);


}
