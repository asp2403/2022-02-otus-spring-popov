package ru.otus.homework.popov.hw5.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id_genre, name from genre", new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("select id_genre, name from genre where id_genre = :id", Map.of("id", id), new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_genre");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
