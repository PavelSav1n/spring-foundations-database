package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.dao.UserDaoImpl;
import ru.itsjava.domain.User;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJdbcHomeworkApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcHomeworkApplication.class, args);

        UserDaoImpl userDao = context.getBean(UserDaoImpl.class);
        // must be 4 users
        System.out.println("userDao.count() = " + userDao.count());
        // insert user
        User newUser = new User("Dart Vaider", 60);
        userDao.insert(newUser);
        System.out.println("New user (userDao.findUserById(5)) = " + userDao.findUserById(5));
        // now must be 5 users
        System.out.println("userDao.count() = " + userDao.count());
        // update user
        newUser.setName("Luke Skywalker");
        newUser.setAge(33);
        userDao.updateById(newUser, 5);
        System.out.println("Updated user (userDao.findUserById(5)) = " + userDao.findUserById(5));
        // delete user
        User firstUser = new User("Peter Parker", 25);
        userDao.delete(firstUser);
        // http://localhost:8082/ -- если окно не открывается в браузере
        Console.main(args);
    }

}
