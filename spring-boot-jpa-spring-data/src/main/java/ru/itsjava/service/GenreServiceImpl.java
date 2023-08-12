package ru.itsjava.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itsjava.domain.Genre;
import ru.itsjava.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public void changeGenreByName(String oldName, String newName) {
        Genre genreToBeUpdated = genreRepository.getByName(oldName).get();
        genreToBeUpdated.setName(newName);
        genreRepository.save(genreToBeUpdated); // короче почему-то работает и без сохранения. Тут написано, что из-за одной сессии -- https://docs.jboss.org/hibernate/core/3.6/reference/en-US/html/objectstate.html#objectstate-modifying
        System.out.println("Successfully updated!");
    }

    @Transactional(readOnly = true)
    @Override
    public void printGenreByName(String genreName) {
        System.out.println(genreRepository.getByName(genreName).get());
    }

    @Override
    public void printAll() {
        genreRepository.findAll().forEach(System.out::println);
    }

    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id).get();
    }
}
