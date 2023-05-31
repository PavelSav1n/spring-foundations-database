package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.dao.StudentDao;
import ru.itsjava.domain.Student;

import java.sql.SQLException;

@SpringBootApplication
public class SpringBootJdbcFoundationsApplication {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcFoundationsApplication.class, args);

        StudentDao studentDao = context.getBean(StudentDao.class);

        System.out.println("studentDao.count() = " + studentDao.count());

        Student newStudent = new Student("Jimmy Fallon", 50);
        studentDao.insert(newStudent);
        // +1
        System.out.println("studentDao.count() = " + studentDao.count());

        // Обновляем Иванова из БД (id = 1)
        Student updatedStudent = new Student("Ivanov Updated", 123);
        studentDao.updateById(updatedStudent, 1);

        // Удаляем Иванова:
        studentDao.delete(updatedStudent);

        System.out.println("studentDao.count() = " + studentDao.count());

        // http://localhost:8082/ -- если окно не открывается в браузере
        Console.main(args);

    }

}
