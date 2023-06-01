package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {
    // можно использовать JdbcTemplate -- родителя JdbcOperations
    // чтобы использовать конструкцию типа:
    //    private final JdbcOperations jdbc;
    //    jdbc.update("insert into students (fio, age) values (?,?)", student.getFio(), student.getAge());
    private final NamedParameterJdbcOperations jdbc; // Если нужны именованные параметры, то можно использовать этот интерфейс

    @Override
    public int count() {
        // <T> T queryForObject(String sql, Class<T> requiredType)
        return jdbc.getJdbcOperations().queryForObject("select count(*) from students", Integer.class);
        // getJdbcOperations не работает с параметрами, как в методах ниже.
    }

    @Override
    public void insert(Student student) {
        // JdbcOperation version:
        // int update(String sql, @Nullable Object... args)
//        jdbc.update("insert into students (fio, age) values (?,?)", student.getFio(), student.getAge());

        // named parameters:
        Map<String, Object> params = Map.of("fio", student.getFio(), "age", student.getAge());
        jdbc.update("insert into students (fio, age) values (:fio, :age)", params);

    }


    @Override
    public void updateById(Student student, long id) {
        Map<String, Object> params = Map.of("id", id, "fio", student.getFio(), "age", student.getAge());
        jdbc.update("update students set fio = :fio, age = :age where id = :id", params);

    }

    @Override
    public void delete(Student student) {
        Map<String, Object> params = Map.of("fio", student.getFio());
        jdbc.update("delete from students where fio = :fio", params);
    }

    @Override
    public Student findById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("select id, fio, age from students where id = :id", params, new StudentMapper());
    }


    // Для мапинга необходимо имплементировать RowMapper:
    private static class StudentMapper implements RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Student(rs.getLong("id"), rs.getString("fio"), rs.getInt("age"));
        }
    }

}
