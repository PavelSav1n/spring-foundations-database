package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.dao.UserDaoImpl;
import ru.itsjava.domain.Pet;
import ru.itsjava.domain.User;
import ru.itsjava.services.UserService;
import ru.itsjava.services.UserServiceImpl;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJdbcHomeworkApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcHomeworkApplication.class, args);

        UserService userService = context.getBean(UserService.class);

        userService.insert(new User("Iron Man", 45));
        userService.insert(new User("Clark Kent", 33, new Pet(1, "dog")));

        // Iron Man without pet:
        System.out.println("userService.findUserById(5) = " + userService.findUserById(5));
        // Clark Kent with pet:
        System.out.println("userService.findUserById(6) = " + userService.findUserById(6));

        // http://localhost:8082/ -- если окно не открывается в браузере
//        Console.main(args);
    }

}
