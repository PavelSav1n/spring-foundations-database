package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Student;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {
    // можно использовать JdbcTemplate -- родителя JdbcOperations
    private final JdbcOperations jdbc;

    @Override
    public int count() {
        // <T> T queryForObject(String sql, Class<T> requiredType)
        return jdbc.queryForObject("select count(*) from students", Integer.class);
    }

    @Override
    public void insert(Student student) {
        // int update(String sql, @Nullable Object... args)
        jdbc.update("insert into students (fio, age) values (?,?)", student.getFio(), student.getAge());
    }

    @Override
    public void updateById(Student student, long id) {
        jdbc.update("update students set fio = ?, age = ? where id = ?", student.getFio(), student.getAge(), id);

    }

    @Override
    public void delete(Student student) {
        jdbc.update("delete from students where fio = ?", student.getFio());
    }
}
