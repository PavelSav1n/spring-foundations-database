package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Faculty;
import ru.itsjava.domain.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public long insert(Student student) {
        // JdbcOperation version:
        // int update(String sql, @Nullable Object... args)
//        jdbc.update("insert into students (fio, age) values (?,?)", student.getFio(), student.getAge());

        // KeyHolder -- ArrayList, который будет хранить возвращённые из БД ключи для новых вставленных пользователей. Возвращает тип Number.
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Для jdbc.update() с KeyHolder нужен MapSqlParameterSource()

        // named parameters:
        Map<String, Object> params = Map.of("fio", student.getFio(), "age", student.getAge(), "faculty_id", student.getFaculty().getId());
        jdbc.update("insert into students (fio, age, faculty_id) values (:fio, :age, :faculty_id)", new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().longValue();
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
        return jdbc.queryForObject("select s.id as s_ID, fio, age, name, f.id as f_ID from students s, faculties f where s.id = :id and faculty_id = f.id", params, new StudentMapper());
    }

    @Override
    public List<Student> findAll() {
        return jdbc.query("select s.id as s_ID, fio, age, name, f.id as f_ID from students s, faculties f where faculty_id = f.id", new StudentMapper());
    }


    // Для мапинга необходимо имплементировать RowMapper:
    // Обязательно проверять название столбцов результирующей таблицы запроса. Особенно с id полями разных таблиц. Лучше явно переписать алиасы, типа s_ID и f_ID
    private static class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Student(rs.getLong("s_ID"), rs.getString("fio"), rs.getInt("age"), new Faculty(rs.getLong("f_ID"), rs.getString("name")));
        }
    }

}
