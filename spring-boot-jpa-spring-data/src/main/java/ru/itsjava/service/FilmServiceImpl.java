package ru.itsjava.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itsjava.domain.Film;
import ru.itsjava.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Transactional(readOnly = true)
    @Override
    public void printAllFilms() {
        List<Film> filmList = filmRepository.findAll();

        for (int i = 0; i < filmList.size(); i++) {
            System.out.println(filmList.get(i));
        }

    }

    // crud

    @Override
    public Film create(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Optional<Film> findById(long id) {
        return filmRepository.findById(id);
    }


}
