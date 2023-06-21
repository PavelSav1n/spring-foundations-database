package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;
import ru.itsjava.noservice.NoService;
import ru.itsjava.repository.PetRepository;
import ru.itsjava.repository.UserRepository;
import ru.itsjava.services.PetService;
import ru.itsjava.services.UserService;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJpaSpringDataHwApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJpaSpringDataHwApplication.class, args);

        // created Services with @Transactional annotations over methods:
        PetService petService = context.getBean(PetService.class);
        UserService userService = context.getBean(UserService.class);

        petService.printAll();
        userService.printAll();

        // some changes:
        petService.create(new Pet(5L,"elephant", 4L));
        User userToBeUpdated = userService.findById(1L);
        userToBeUpdated.setName("Frodo Baggins");
        userService.update(userToBeUpdated);

        // voila:
        System.out.println();
        petService.printAll();
        userService.printAll();


        // from previous practice
        // Plain JPA Data without Services:
//		context.getBean(NoService.class).NoServiceJpaData();
//
//		Console.main(args);
    }


}
