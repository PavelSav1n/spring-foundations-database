package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Genre;

@DataJpaTest
@Import(GenreRepositoryImpl.class)
public class GenreRepositoryImplTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void shouldHaveCorrectMethodGetById() {
        Genre expectedGenre = entityManager.find(Genre.class, 1L);
        Genre actualGenre = genreRepository.getById(1L);

        Assertions.assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void shouldHaveCorrectMethodInsert(){
        Genre expectedGenre = new Genre(4L, "cartoon");
        genreRepository.insert(expectedGenre);
        Genre actualGenre = genreRepository.getById(4L);

        Assertions.assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void shouldHaveCorrectMethodUpdate(){
        Genre genreToUpdate = genreRepository.getById(2L);
        genreToUpdate.setName("CHANGED_GENRE_NAME");
        genreRepository.update(genreToUpdate);

        Assertions.assertEquals("CHANGED_GENRE_NAME", genreToUpdate.getName());
    }

    @Test
    public void shouldHaveCorrectMethodDelete(){
        genreRepository.delete(3L);

        Assertions.assertNull(genreRepository.getById(3L));
    }
}
