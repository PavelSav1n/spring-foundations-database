package ru.itsjava;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itsjava.domain.Faculty;
import ru.itsjava.domain.Student;
import ru.itsjava.services.AppService;
import ru.itsjava.services.StudentService;

@SpringBootApplication
public class SpringBootJdbcFoundationsApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJdbcFoundationsApplication.class, args);

        context.getBean(AppService.class).start();

//        StudentService student = context.getBean(StudentService.class);
//        student.insert(new Student("Test Student", 99, new Faculty(1, "Java Development")));
//
//
//        Student firstStudent = student.findById(1);
//        Student secondStudent = student.findById(2);
//        Student thirdStudent = student.findById(3);
//
//        System.out.println("firstStudent = " + firstStudent);
//        System.out.println("secondStudent = " + secondStudent);
//        System.out.println("thirdStudent = " + thirdStudent);


//        StudentDao studentDao = context.getBean(StudentDao.class);
//
//        System.out.println("studentDao.count() = " + studentDao.count());
//
//        Student newStudent = new Student("Jimmy Fallon", 50);
//        studentDao.insert(newStudent);
//        // +1
//        System.out.println("studentDao.count() = " + studentDao.count());
//        System.out.println("studentDao.findById(5) -- new student = " + studentDao.findById(5));
//
//        // Обновляем Иванова из БД (id = 1)
//        Student updatedStudent = new Student("Ivanov Updated", 123);
//        studentDao.updateById(updatedStudent, 1);
//
//        System.out.println("studentDao.findById(1) = " + studentDao.findById(1));
//
//        // Удаляем Иванова:
//        studentDao.delete(updatedStudent);
//
//        System.out.println("studentDao.count() = " + studentDao.count());
//
//        // http://localhost:8082/ -- если окно не открывается в браузере
//        Console.main(args);

    }

}
