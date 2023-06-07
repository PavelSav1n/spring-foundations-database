package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {
    private final IOService ioService;
    private final UserService userService;
    private final PetService petService;


    @Override
    public void start() {
        System.out.println("Welcome to Find Your Pet service!");
        while (true) {
            System.out.println("""
                                        
                    Select menu item:
                    1 -- print all users
                    2 -- print all pets
                    3 -- add new user
                    4 -- add new pet
                    any№ -- exit
                    """);
            int menuItem = ioService.inputInt();
            if (menuItem == 1) {
                printAllUsers();
            } else if (menuItem == 2) {
                printAllPets();
            } else if (menuItem == 3) {
                addNewUser();
            } else if (menuItem == 4) {
                addNewPet();
            } else System.exit(0);
        }
    }

    @Override
    public void printAllUsers() {
        System.out.println("\nList of all users:");
        for (User elem : userService.findAll()) {
            System.out.println(elem);
        }
    }

    @Override
    public void printAllPets() {
        System.out.println("\nList of all pets:");
        for (Pet elem : petService.findAll()) {
            System.out.println(elem);
        }
    }

    @Override
    public void addNewUser() {
        System.out.println("\nAdding new User...");
        System.out.println("Enter name of new User:");
        String userName = ioService.input();
        System.out.println("Enter age of new User:");
        int userAge = ioService.inputInt();
        System.out.println("Choose a pet by 'id', if necessary. Enter '0' if not needed:");
        printAllPets();
        int usersPetID = ioService.inputInt();
        try { // если введён корректный ID пета, то создаём пользователя с петом
            Pet usersPet = petService.findById(usersPetID);
            User newUser = new User(userName, userAge, usersPet);
            userService.insert(newUser);
            System.out.println(newUser + " added successfully!");

        } catch (EmptyResultDataAccessException exception) {
            User newUser = userService.insert(new User(userName, userAge)); // если не корректный ID пета, то без
            System.out.println(newUser + " added successfully!");
        }
    }

    @Override
    public void addNewPet() {
        System.out.println("\nAdding new Pet...");
        System.out.println("Enter species of a new Pet:");
        String petsSpecies = ioService.input();
        petService.insert(new Pet(petsSpecies));
        System.out.println(petsSpecies + " added successfully!");
    }
}
