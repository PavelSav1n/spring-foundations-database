package ru.itsjava.dao;

import ru.itsjava.domain.Student;

public interface StudentDao {

    int count();

    void insert(Student student);

    void updateById(Student student, long id);

    void delete(Student student);

    Student findById(long id);

}
