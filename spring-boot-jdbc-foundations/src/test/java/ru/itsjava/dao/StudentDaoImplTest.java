package ru.itsjava.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.itsjava.domain.Faculty;
import ru.itsjava.domain.Student;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(StudentDaoImpl.class) // beans in this class are available now from here
public class StudentDaoImplTest {

    private static final String DEFAULT_NAME = "New Student";
    private static final int DEFAULT_AGE = 44;
    private static final long THIRD_ID = 3L;
    private static final long SECOND_ID = 2L;
    private static final long FIRST_ID = 1L;
    private static final Faculty DEFAULT_FACULTY = new Faculty(1L, "Android Development");

    @Autowired
    private StudentDao studentsDao; // autowire this bean

    @Test
    void shouldHaveCorrectMethodCount() {
        assertEquals(2, studentsDao.count());
    }

    @Test
    void shouldHaveCorrectMethodInsert() {
        Student expectedStudent = new Student(THIRD_ID, DEFAULT_NAME, DEFAULT_AGE, DEFAULT_FACULTY);
        long idFromDB = studentsDao.insert(expectedStudent);
        System.out.println("idFromDB = " + idFromDB);
        Student actualStudent = studentsDao.findById(THIRD_ID);

        assertAll(
                () -> assertEquals(expectedStudent.getFio(), actualStudent.getFio()),
                () -> assertEquals(expectedStudent.getAge(), actualStudent.getAge()));
    }

    @Test
    void shouldHaveCorrectMethodUpdateById() {
        Student expectedStudent = new Student(SECOND_ID, DEFAULT_NAME, DEFAULT_AGE, DEFAULT_FACULTY);
        studentsDao.updateById(expectedStudent, SECOND_ID);
        Student actualStudent = studentsDao.findById(SECOND_ID);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldHaveCorrectMethodDelete() {
        Student studentToBeDeleted = studentsDao.findById(FIRST_ID);
        studentsDao.delete(studentToBeDeleted);
        int actualStudentsCount = studentsDao.count();

        assertEquals(1, actualStudentsCount);

    }
}
