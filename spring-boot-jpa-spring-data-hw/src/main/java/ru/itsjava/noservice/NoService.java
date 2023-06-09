package ru.itsjava.noservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;
import ru.itsjava.repository.PetRepository;
import ru.itsjava.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class NoService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public void NoServiceJpaData() {

        System.out.println("**************** findByID test **************");
        System.out.println("userRepository.findByID(1L) = " + userRepository.findById(1L));

        System.out.println("**************** save + findAll test **************");
        User testUser = new User(0L, "TestUser", 99);
        userRepository.save(testUser);

        printAllUsers(userRepository); // findAll() test

        System.out.println("**************** save test **************");
        User userToBeUpdated = userRepository.getById(1L);
        userToBeUpdated.setName("UPDATED NAME");
        userRepository.save(userToBeUpdated);

        printAllUsers(userRepository);

        System.out.println("**************** delete test **************");
        userRepository.deleteById(5L);

        printAllUsers(userRepository);

        System.out.println("#################### pet repository test ####################");

        System.out.println("**************** findByID test **************");
        System.out.println("petRepository.findByID(1L) = " + petRepository.findById(1L));

        System.out.println("**************** save + findAll test **************");
        Pet testPet = new Pet(0L, "TestPet", 1);
        petRepository.save(testPet);

        printAllPets(petRepository); // findAll() test

        System.out.println("**************** save test **************");
        Pet petToBeUpdated = petRepository.getById(1L);
        petToBeUpdated.setSpecies("UPDATED NAME");
        petRepository.save(petToBeUpdated);

        printAllPets(petRepository);

        System.out.println("**************** delete test **************");
        petRepository.deleteById(5L);

        printAllPets(petRepository);
    }

    private static void printAllUsers(UserRepository userRepository) {
        for (User elem : userRepository.findAll()) {
            System.out.println("elem = " + elem);
        }
    }

    private static void printAllPets(PetRepository petRepository) {
        for (Pet elem : petRepository.findAll()) {
            System.out.println("elem = " + elem);
        }
    }

}
