package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.User;

import java.util.List;

@DataJpaTest
@Import(UserRepositoryImpl.class)
public class UserRepositoryImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldHaveCorrectMethodInsert() {
        User expectedUser = new User(5L, "INSERTED_USER", 55);
        userRepository.insert(expectedUser);
        User actualUser = userRepository.findById(5L);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldHaveCorrectMethodFindById() {
        User expectedUser = entityManager.find(User.class, 1L);
        User actualUser = userRepository.findById(1L);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldHaveCorrectMethodFindAll() {
        List expectedUserList = entityManager.createQuery("SELECT u FROM users u", User.class).getResultList();
        List<User> actualUserList = userRepository.findAll();

        Assertions.assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void shouldHaveCorrectMethodFindAllWithoutPets() {
        List expectedUserList = entityManager.createQuery("SELECT u FROM users u WHERE u.pets IS EMPTY", User.class).getResultList();
        List<User> actualUserList = userRepository.findAllWithoutPets();

        Assertions.assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void shouldHaveCorrectMethodFindAllWithPets() {
        List<User> expectedUserList = entityManager.createQuery(("SELECT DISTINCT u FROM users u JOIN FETCH pets p"), User.class).getResultList();
        List<User> actualUserList = userRepository.findAllWithPets();

        Assertions.assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void shouldHaveCorrectMethodUpdate() {
        User userToUpdate = userRepository.findById(2L);
        userToUpdate.setName("UPDATED_USER_NAME");
        userRepository.update(userToUpdate);

        Assertions.assertEquals("UPDATED_USER_NAME", userRepository.findById(2L).getName());
    }

    @Test
    public void shouldHaveCorrectMethodDelete() {
        userRepository.delete(4L);

        Assertions.assertNull(userRepository.findById(4L));
    }
}
