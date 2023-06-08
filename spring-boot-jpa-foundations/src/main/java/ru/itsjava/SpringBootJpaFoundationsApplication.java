package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJpaFoundationsApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringBootJpaFoundationsApplication.class, args);
        Console.main(args);
    }

}
