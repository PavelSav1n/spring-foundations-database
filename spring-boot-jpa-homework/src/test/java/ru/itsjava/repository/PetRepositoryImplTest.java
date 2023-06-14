package ru.itsjava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Pet;

import java.util.List;

@DataJpaTest
@Import(PetRepositoryImpl.class)
public class PetRepositoryImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void shouldHaveCorrectMethodFindById() {
        Pet expectedPet = entityManager.find(Pet.class, 1L);
        Pet actualPet = petRepository.findById(1L);

        Assertions.assertEquals(expectedPet, actualPet);
    }

    @Test
    public void shouldHaveCorrectMethodInsert() {
        Pet expectedPet = new Pet(5L, "Inserted by insert() Pet", 1);
        petRepository.insert(expectedPet);
        Pet actualPet = petRepository.findById(5L);

        Assertions.assertEquals(expectedPet, actualPet);
    }

    @Test
    public void shouldHaveCorrectMethodFindAll() {
        List expectedPetList = entityManager.createQuery("SELECT p FROM pets p").getResultList();
        List<Pet> actualPetList = petRepository.findAll();

        Assertions.assertEquals(expectedPetList, actualPetList);
    }

    @Test
    public void shouldHaveCorrectMethodUpdate() {
        Pet petToUpdate = petRepository.findById(2L);
        petToUpdate.setSpecies("UPDATED_PET");
        petRepository.update(petToUpdate);

        Assertions.assertEquals("UPDATED_PET", petRepository.findById(2L).getSpecies());
    }

    @Test
    public void shouldHaveCorrectMethodDelete() {
        petRepository.delete(4L);

        Assertions.assertNull(petRepository.findById(4L));
    }

}
