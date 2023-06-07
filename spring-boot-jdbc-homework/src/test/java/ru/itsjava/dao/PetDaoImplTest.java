package ru.itsjava.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Pet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest // Если использовать @SpringBootTest, то для каждого теста ниже будет использована одна и та же БД. @JdbcTest так же использует одна и та же база в рамках работы программы теста, однако перед каждым методом откатывает изменения. При этом KeyHolder инкрементится.
@Import(PetDaoImpl.class)
public class PetDaoImplTest {

    public static final String DEFAULT_SPECIES = "Inserted Pet Species";
    public static final long FIRST_ID = 1L;
    public static final String FIRST_PET_SPECIES = "dog"; // actual data from data.sql

    @Autowired
    private PetDao petDao;

    @Test
    @DisplayName("Проверка count()")
    void shouldHaveCorrectMethodCount() {
        assertEquals(2, petDao.count());
    }

    @Test
    @DisplayName("Проверка insert()")
    void shouldHaveCorrectMethodInsert() {
        // Поскольку нашёл баг в H2 / @JdbcTest использую такой метод проверки:
        Pet expectedPet = petDao.insert(new Pet(DEFAULT_SPECIES));
        Pet actualPet = petDao.findById(expectedPet.getId());
        System.out.println("petDao.count() = " + petDao.count()); // тут будет 3 пета
        System.out.println("expectedPet = " + expectedPet); // а тут будет id = 4
        System.out.println("actualPet = " + actualPet); // потому что видимо ещё один petDao.insert отработал до этого теста.

        assertEquals(expectedPet, actualPet);
    }

    @Test
    @DisplayName("Проверка updateById()")
    void shouldHaveCorrectMethodUpdateById() {
        Pet expectedPet = new Pet(FIRST_ID, DEFAULT_SPECIES);
        petDao.updateById(expectedPet, FIRST_ID);
        Pet actualPet = petDao.findById(FIRST_ID);

        assertEquals(expectedPet, actualPet);
    }

    @Test
    @DisplayName("Проверка delete()")
    void shouldHaveCorrectMethodDelete() {
        // Мы не можем удалять петов, если у них есть хозяин. Поэтому создаём пета у которого нет хозяина:
        Pet newPet = new Pet("Elephant");
        newPet = petDao.insert(newPet); // тут возвращается тот же самый пет, только с id из базы.
        Pet deletedPet = petDao.delete(newPet.getId()); // явно указываем id удаляемого пета
        System.out.println("deletedPet = " + deletedPet);
        Pet petToBeDeleted = newPet;

        assertAll(() -> assertEquals(petToBeDeleted, deletedPet),
                () -> assertEquals(2, petDao.count()));
    }

    @Test
    @DisplayName("Проверка findById()")
    void shouldHaveCorrectMethodFindById() {
        Pet excpectedPet = new Pet(FIRST_ID, FIRST_PET_SPECIES);
        Pet actualPet = petDao.findById(FIRST_ID);

        assertEquals(excpectedPet, actualPet);
    }
}
