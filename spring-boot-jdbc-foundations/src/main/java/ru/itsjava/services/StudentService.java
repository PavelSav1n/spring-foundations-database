package ru.itsjava.services;

import ru.itsjava.domain.Student;

import java.util.List;

public interface StudentService {
    void insert(Student student);

    Student findById(long id);

    List<Student> findAll();
}
