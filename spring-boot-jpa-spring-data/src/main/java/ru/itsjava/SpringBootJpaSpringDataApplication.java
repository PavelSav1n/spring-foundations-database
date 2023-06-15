package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.domain.Film;
import ru.itsjava.domain.Genre;
import ru.itsjava.repository.FilmRepository;
import ru.itsjava.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringBootJpaSpringDataApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaSpringDataApplication.class, args);

        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        System.out.println("genreRepository.findById(1L) = " + genreRepository.findById(1L));

        Genre genre = new Genre(0L, "thriller");
        genreRepository.save(genre);
        System.out.println("genreRepository.findById(3L) = " + genreRepository.findById(3L));

        Genre genre2 = new Genre(0L, "thriller");
        genreRepository.save(genre2);

        System.out.println("genreRepository.findById(4L) = " + genreRepository.findById(4L));

        genre2.setName("NotThriller");

        genreRepository.save(genre2);

        System.out.println("genreRepository.findById(3L) = " + genreRepository.findById(3L));
        System.out.println("genreRepository.findById(4L) = " + genreRepository.findById(4L));


        genreRepository.deleteById(4L);

        FilmRepository filmRepository = context.getBean(FilmRepository.class);

        List<Film> filmRepositoryAll = filmRepository.findAll();
        for (Film elem : filmRepositoryAll) {
            System.out.println("elem = " + elem);
        }

        System.out.println("genreRepository.findById(4L).isEmpty() = " + genreRepository.findById(4L).isEmpty());


        // Кастомный метод запроса Spring Data:

        System.out.println("genreRepository.getByName(\"comedy\") = " + genreRepository.getByName("comedy"));

        System.out.println("filmRepository.findByTitleAndGenre(\"Harry Potter\", genreRepository.getById(1L)) = " +
                filmRepository.findByTitleAndGenre("Harry Potter", genreRepository.getById(1L)).isPresent());

//		Console.main(args);
    }

}
