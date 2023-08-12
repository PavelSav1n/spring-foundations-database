package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.itsjava.domain.Film;
import ru.itsjava.domain.Genre;
import ru.itsjava.domain.Place;
import ru.itsjava.jpadataexamples.JpaDataExamples;
import ru.itsjava.service.FilmService;
import ru.itsjava.service.GenreService;

import java.util.ArrayList;

@SpringBootApplication
public class SpringBootJpaSpringDataApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaSpringDataApplication.class, args);


        FilmService filmService = context.getBean(FilmService.class);
        filmService.printAllFilms();

        GenreService genreService = context.getBean(GenreService.class);
        genreService.printAll();



        Film film = new Film(0L, "Test", genreService.getById(1), null);
        filmService.create(film);

        filmService.findById(3);
        filmService.printAllFilms();


        // Это для меня
        // Если мы не используем в сервисах @Transactional то
        // в application.properties необходимо установить флаг spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
//        JpaDataExamples jpaDataExamples = context.getBean(JpaDataExamples.class);
//        jpaDataExamples.printAllJpaDataExamples();
//		Console.main(args);
    }

}
