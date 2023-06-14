package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Film;

import java.util.List;

@DataJpaTest
@Import(FilmRepositoryImpl.class)
public class FilmRepositoryImplTest {

    @PersistenceContext // контекст из domain основной программы
    private EntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void shouldHaveCorrectMethodFindAll(){
        List<Film> expectedList = entityManager.createQuery("SELECT DISTINCT f FROM films f JOIN FETCH f.genre JOIN FETCH f.places ", Film.class).getResultList();
        List<Film> actualList = filmRepository.findAll();

        Assertions.assertEquals(expectedList, actualList);
    }

}
