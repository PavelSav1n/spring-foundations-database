package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.itsjava.dao.FacultyDao;
import ru.itsjava.domain.Faculty;
import ru.itsjava.domain.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {
    private final StudentService studentService;
    private final IOService ioService;
    private final FacultyDao facultyDao;

    @Override
    public void start() {
        System.out.println("Welcome to our university!");
        while (true) {
            System.out.println("" +
                    "1 -- вывести список всех студентов\n" +
                    "2 -- добавить нового студента\n" +
                    "any_num -- выход");
            System.out.println("Введите номер меню:");
            int menuNum = ioService.inputInt();
            if (menuNum == 1) {
                printAllStudents();
            } else if (menuNum == 2) {
                insertStudent();
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void printAllStudents() {
        System.out.println("Список студентов:");
        List<Student> studentList = studentService.findAll();
        for (Student elem : studentList) {
            System.out.println(elem);
        }

    }

    @Override
    public void insertStudent() {
        System.out.println("Создание студента:");
        System.out.println("Введите ФИО студента:");
        String fio = ioService.input();
        System.out.println("Введите возраст:");
        int age = ioService.inputInt();
        System.out.println("Введите факультет:");
        String faculty = ioService.input();
        // На случай неверного ввода имени факультета:
        try {
            Faculty studentsFaculty = facultyDao.findByName(faculty);
            studentService.insert(new Student(fio, age, studentsFaculty));
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("There is no '" + faculty + "' faculty in DB\n");
        }
    }
}
