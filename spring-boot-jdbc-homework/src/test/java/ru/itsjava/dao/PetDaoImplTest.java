package ru.itsjava.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Pet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@JdbcTest // Если использовать @SpringBootTest, то для каждого теста ниже будет использована одна и та же БД. @JdbcTest видимо создаёт отдельные копии и изменения БД в одном тесте, не влияют на БД в другой, за исключение инкремента keyholder'а
@Import(PetDaoImpl.class)
public class PetDaoImplTest {

    public static final String DEFAULT_SPECIES = "Inserted Pet Species";
    public static final long FIRST_ID = 1L;
    public static final long THIRD_ID = 3L;
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
        Pet petToBeDeleted = new Pet(THIRD_ID, "Elephant");
        petDao.insert(petToBeDeleted);
        Pet deletedPet = petDao.delete(THIRD_ID);
        System.out.println("deletedPet = " + deletedPet);

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
