package ru.itsjava.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(UserDaoImpl.class) // импортируем обязательно тот класс, который имплементировали
public class UserDaoImplTest {

    public static final String DEFAULT_USER = "Inserted User";
    public static final int DEFAULT_AGE = 55;
    public static final int FIRST_ID = 1;
    public static final int FIRST_USER_AGE = 22; // actual data from data.sql
    public static final String FIRST_USER_NAME = "Test Man 1"; // actual data from data.sql
    public static final Pet DEFAULT_PET = new Pet(1, "dog"); // actual data from data.sql

    @Autowired
    private UserDao userDao;

    @Test
    void shouldHaveCorrectMethodCount() {
        assertEquals(2, userDao.count());
    }

    @Test
    void shouldHaveCorrectMethodInsert() {
        // Проверяем пользователя без пета:
        User expectedUserWithoutPet = userDao.insert(new User(DEFAULT_USER, DEFAULT_AGE)); // возвращаем пользователя с актуальным id из БД
        User actualUserWithoutPet = userDao.findById(expectedUserWithoutPet.getId());

        // Проверяем пользователя с петом:
        User expectedUserWithPet = userDao.insert(new User(DEFAULT_USER, DEFAULT_AGE, DEFAULT_PET)); // возвращаем пользователя с актуальным id из БД
        User actualUserWithPet = userDao.findById(expectedUserWithPet.getId());

        assertAll(() -> assertEquals(expectedUserWithoutPet, actualUserWithoutPet),
                () -> assertEquals(expectedUserWithPet, actualUserWithPet));
    }

    @Test
    void shouldHaveCorrectMethodUpdateById() {
        User expectedUser = userDao.insert(new User(DEFAULT_USER, DEFAULT_AGE, DEFAULT_PET));
        userDao.updateById(expectedUser, expectedUser.getId());
        User actualUser = userDao.findById(expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldHaveCorrectMethodDelete() {
        User userToBeDeleted = new User(FIRST_ID, FIRST_USER_NAME, FIRST_USER_AGE);
        userDao.delete(userToBeDeleted);
        int actualUsersCount = userDao.count();

        assertEquals(1, actualUsersCount);
    }

    @Test
    void shouldHaveCorrectMethodFindById() {
        User excpectedUser = new User(FIRST_ID, FIRST_USER_NAME, FIRST_USER_AGE, DEFAULT_PET);
        User actualUser = userDao.findById(FIRST_ID);

        assertEquals(excpectedUser, actualUser);
    }

}
