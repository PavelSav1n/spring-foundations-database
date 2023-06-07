package ru.itsjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.services.AppService;

@SpringBootApplication
public class SpringBootJdbcFoundationsApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcFoundationsApplication.class, args);
        context.getBean(AppService.class).start();

//        // http://localhost:8082/ -- если окно не открывается в браузере
//        Console.main(args);
    }
}
