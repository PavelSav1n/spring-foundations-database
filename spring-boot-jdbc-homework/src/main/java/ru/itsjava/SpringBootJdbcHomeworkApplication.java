package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.services.AppService;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJdbcHomeworkApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcHomeworkApplication.class, args);

        context.getBean(AppService.class).start();

//         http://localhost:8082/ -- если окно не открывается в браузере
//        Console.main(args);
    }

}
