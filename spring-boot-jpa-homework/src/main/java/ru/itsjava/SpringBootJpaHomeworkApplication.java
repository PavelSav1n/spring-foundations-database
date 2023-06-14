package ru.itsjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;
import ru.itsjava.repository.PetRepository;
import ru.itsjava.repository.UserRepository;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJpaHomeworkApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaHomeworkApplication.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class);

        System.out.println("**************** findByID test **************");
        System.out.println("userRepository.findByID(1L) = " + userRepository.findById(1L));

        System.out.println("**************** insert + findAll test **************");
        User testUser = new User(0L, "TestUser", 99);
        userRepository.insert(testUser);
        printAllUsers(userRepository); // findAll() test

        System.out.println("**************** update test **************");
        User userToBeUpdated = userRepository.findById(1L);
        userToBeUpdated.setName("UPDATED NAME");
        userRepository.update(userToBeUpdated);
        printAllUsers(userRepository);

        System.out.println("**************** delete test **************");
        userRepository.delete(5L);
        printAllUsers(userRepository);

        System.out.println("#################### pet repository test ####################");
        PetRepository petRepository = context.getBean(PetRepository.class);
        System.out.println("**************** findByID test **************");
        System.out.println("petRepository.findByID(1L) = " + petRepository.findById(1L));

        System.out.println("**************** insert + findAll test **************");
        Pet testPet = new Pet(0L, "TestPet", 1);
        petRepository.insert(testPet);
        printAllPets(petRepository); // findAll() test

        System.out.println("**************** update test **************");
        Pet petToBeUpdated = petRepository.findById(1L);
        petToBeUpdated.setSpecies("UPDATED NAME");
        petRepository.update(petToBeUpdated);
        printAllPets(petRepository);

        System.out.println("**************** delete test **************");
        petRepository.delete(5L);
        printAllPets(petRepository);


//        Console.main(args);
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
