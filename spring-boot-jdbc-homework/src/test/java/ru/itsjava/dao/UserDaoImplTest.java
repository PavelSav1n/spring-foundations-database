package ru.itsjava.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(UserDaoImpl.class) // импортируем обязательно тот класс, который имплементировали
public class UserDaoImplTest {

    public static final String DEFAULT_USER = "Inserted User";
    public static final int DEFAULT_AGE = 55;
    public static final long THIRD_ID = 3L;
    public static final int FIRST_ID = 1;
    public static final int FIRST_USER_AGE = 22; // actual data from data.sql
    public static final String FIRST_USER_NAME = "Test Man 1"; // actual data from data.sql

    @Autowired
    private UserDao userDao;

    @Test
    void shouldHaveCorrectMethodCount() {
        int actualUsersCount = userDao.count();

        assertEquals(2, actualUsersCount);
    }

    @Test
    void shouldHaveCorrectMethodInsert() {
        User expectedUser = new User(THIRD_ID, DEFAULT_USER, DEFAULT_AGE);
        userDao.insert(expectedUser);
        User actualUser = userDao.findUserById(THIRD_ID);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldHaveCorrectMethodUpdateById() {
        User expectedUser = new User(FIRST_ID, DEFAULT_USER, DEFAULT_AGE);
        userDao.updateById(expectedUser, FIRST_ID);
        User actualUser = userDao.findUserById(FIRST_ID);

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
        User excpectedUser = new User(FIRST_ID, FIRST_USER_NAME, FIRST_USER_AGE);
        User actualUser = userDao.findUserById(FIRST_ID);

        assertEquals(excpectedUser, actualUser);
    }

}
