package ru.itsjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itsjava.domain.Film;
import ru.itsjava.domain.Genre;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    // По сути все стандартные запросы уже реализованы в JpaRepository,
    // за исключением каких-то кастомных, которые относятся напрямую к полям:

    // Optional явно обрабатывает пустой результат запроса SQL:
    // Для того, чтобы получить тип Film, нужно будет писать .get()
    Optional<Film> findByTitleAndGenre(String title, Genre genre);
}
