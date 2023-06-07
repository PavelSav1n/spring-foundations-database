package ru.itsjava.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itsjava.domain.Pet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PetDaoImpl implements PetDao {
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from pets", Integer.class);
    }

    @Override
    public List<Pet> findAll() {
        return jdbc.query("select id, species from pets", new PetMapper());
    }

    @Override
    public Pet insert(Pet pet) {
        System.out.println("######## insert START ########");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = Map.of("species", pet.getSpecies());
        jdbc.update("insert into pets (species) values(:species)", new MapSqlParameterSource(params), keyHolder);
        return new Pet(keyHolder.getKey().longValue(), pet.getSpecies());
    }

    @Override
    public void updateById(Pet pet, long id) {
        Map<String, Object> params = Map.of("species", pet.getSpecies(), "id", id);
        jdbc.update("update pets set species = :species where id = :id", params);
    }

    // Удаляет пета по id, возвращает, удалённого пета
    @Override
    public Pet delete(long id) {
        Pet deletedPet = findById(id);
        jdbc.update("delete from pets where id = :id", Map.of("id", id));
        return deletedPet;
    }

    @Override
    public Pet findById(long id) {
        System.out.println("******** findById START ********");
        Map<String, Object> param = Map.of("id", id);
        return jdbc.queryForObject("select id, species from pets where id = :id", param, new PetMapper());
    }

    private static class PetMapper implements RowMapper<Pet> {
        @Override
        public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Pet(rs.getLong("id"), rs.getString("species"));
        }
    }
}
