package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.itsjava.jpadataexamples.JpaDataExamples;
import ru.itsjava.service.FilmService;
import ru.itsjava.service.GenreService;

@SpringBootApplication
public class SpringBootJpaSpringDataApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaSpringDataApplication.class, args);


        FilmService filmService = context.getBean(FilmService.class);
        filmService.printAllFilms();

        GenreService genreService = context.getBean(GenreService.class);
        genreService.changeGenreByName("fantasy", "CHANGED_GENRE_FANTASY");
        genreService.printGenreByName("CHANGED_GENRE_FANTASY");

        // Это для меня
        // в application.properties необходимо установить флаг spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
//        JpaDataExamples jpaDataExamples = context.getBean(JpaDataExamples.class);
//        jpaDataExamples.printAllJpaDataExamples();
//		Console.main(args);
    }

}
