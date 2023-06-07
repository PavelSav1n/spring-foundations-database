package ru.itsjava.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
public class AppServiceImplTest {


    public static final Pet PET_1 = new Pet(1, "Pet1");
    public static final Pet PET_2 = new Pet(2, "Pet2");
    public static final Pet PET_3 = new Pet(3, "DummyName");
    public static final User USER_1 = new User(1, "User1", 11, PET_1);
    public static final User USER_2 = new User(2, "User2", 11, PET_2);
    public static final User USER_3 = new User(3, "DummyName", 1, PET_1);


    @Configuration
    static class TestConfiguration {

        @Bean
        public IOService ioService() {
            IOServiceImpl mockIOService = Mockito.mock(IOServiceImpl.class);
            when(mockIOService.input()).thenReturn("DummyName");
            when(mockIOService.inputInt()).thenReturn(1);
            return mockIOService;
        }

        @Bean
        public UserService userService() {
            UserServiceImpl mockUserService = Mockito.mock(UserServiceImpl.class);
            when(mockUserService.findById(1L)).thenReturn(USER_1);
            when(mockUserService.count()).thenReturn(1);
            when(mockUserService.findAll()).thenReturn(Arrays.asList(USER_1, USER_2));
            when(mockUserService.insert(new User("DummyName", 1, PET_1))).thenReturn(USER_3); // поскольку наш input всегда "DummyName", а inputInt = 1
            return mockUserService;
        }

        @Bean
        public PetService petService() {
            PetServiceImpl mockPetService = Mockito.mock(PetServiceImpl.class);
            when(mockPetService.findById(1L)).thenReturn(PET_1);
            when(mockPetService.count()).thenReturn(1);
            when(mockPetService.findAll()).thenReturn(Arrays.asList(PET_1, PET_2));
            when(mockPetService.insert(new Pet("DummyName"))).thenReturn(PET_3);
            return mockPetService;
        }

        @Bean
        public AppService appService(IOService ioService, UserService userService, PetService petService) {
            return new AppServiceImpl(ioService, userService, petService);
        }
    }

    @Autowired
    private AppService appService;

    //    @Test
    // Метод start() будет нерационально сложно проверить, поскольку каждый раз когда мы будем вызывать input() или inputInt() нам нужно будет
    // понимать в каком контексте мы их вызываем и что должно туда передаваться.
//    public void shouldHaveCorrectMethodStart() {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        appService.start();
//        assertEquals("""
//                Welcome to Find Your Pet service!
//
//                Select menu item:
//                1 -- print all users
//                2 -- print all pets
//                3 -- add new user
//                4 -- add new pet
//                any№ -- exit
//
//
//                Process finished with exit code 0
//                """, out.toString());
//    }

    @Test
    public void shouldHaveCorrectMethodPrintAllUsers() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut((new PrintStream(out)));
        appService.printAllUsers();
        // Чтобы не решать проблемы с \r\n или \n используем метод isEqualToNormalizingNewlines()
        assertThat("""
                                
                List of all users:
                User(id=1, name=User1, age=11, pet=Pet(id=1, species=Pet1))
                User(id=2, name=User2, age=11, pet=Pet(id=2, species=Pet2))
                """).isEqualToNormalizingNewlines(out.toString());
    }

    @Test
    public void shouldHaveCorrectMethodPrintAllPets() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut((new PrintStream(out)));
        appService.printAllPets();
        assertThat("""
                                
                List of all pets:
                Pet(id=1, species=Pet1)
                Pet(id=2, species=Pet2)
                """).isEqualToNormalizingNewlines(out.toString());
    }

    @Test
    public void shouldHaveCorrectMethodAddNewUser() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut((new PrintStream(out)));
        appService.addNewUser();
        assertThat("""
                                
                Adding new User...
                Enter name of new User:
                Enter age of new User:
                Choose a pet by 'id', if necessary. Enter '0' if not needed:
                                
                List of all pets:
                Pet(id=1, species=Pet1)
                Pet(id=2, species=Pet2)
                User(id=3, name=DummyName, age=1, pet=Pet(id=1, species=Pet1)) added successfully!
                """).isEqualToNormalizingNewlines(out.toString());
    }

    @Test
    public void shouldHaveCorrectMethodAddNewPet() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut((new PrintStream(out)));
        appService.addNewPet();
        assertThat("""
                                
                Adding new Pet...
                Enter species of a new Pet:
                Pet(id=3, species=DummyName) added successfully!
                """).isEqualToNormalizingNewlines(out.toString());
    }
}
