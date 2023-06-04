package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itsjava.dao.StudentDao;
import ru.itsjava.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    @Override
    public void insert(Student student) {
        System.out.println("Inserting student" + student + "...");
        long studentID = studentDao.insert(student);
        System.out.println("Student " + student.getFio() + " successfully added with id: " + studentID);
    }

    @Override
    public Student findById(long id) {
        return studentDao.findById(id);
    }


}
