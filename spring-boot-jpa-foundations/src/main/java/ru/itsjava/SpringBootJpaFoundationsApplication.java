package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.domain.Film;
import ru.itsjava.domain.Genre;
import ru.itsjava.repository.FilmRepository;
import ru.itsjava.repository.GenreRepository;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class SpringBootJpaFoundationsApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaFoundationsApplication.class, args);

        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        System.out.println("genreRepository.getByIt(1L) = " + genreRepository.getById(1L));

        Genre genre = new Genre(0L, "thriller");
        genreRepository.insert(genre);
        System.out.println("genreRepository.getByID(3L) = " + genreRepository.getById(3L));

        Genre genre2 = new Genre(0L, "thriller");
        genreRepository.insert(genre2);

        System.out.println("genreRepository.getByID(4L) = " + genreRepository.getById(4L));

        genre2.setName("NotThriller");

        genreRepository.update(genre2); // сущность обновляется корректно, несмотря на повторяющиеся поля

        System.out.println("genreRepository.getByID(3L) = " + genreRepository.getById(3L));
        System.out.println("genreRepository.getByID(4L) = " + genreRepository.getById(4L));


        genreRepository.delete(4L);

        FilmRepository filmRepository = context.getBean(FilmRepository.class);

        List<Film> filmRepositoryAll = filmRepository.findAll();
        for (Film elem : filmRepositoryAll) {
            System.out.println("elem = " + elem);
        }

        System.out.println("genreRepository.getByID(4L) = " + genreRepository.getById(4L));
        Console.main(args);
    }

}
