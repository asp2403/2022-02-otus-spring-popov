package ru.otus.homework.popov.hw5.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id_book, title, id_author, id_genre from book", new BookMapper());
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book(title, id_author, id_genre) values (:title, :idAuthor, :idGenre)",
                Map.of("title", book.getTitle(), "idAuthor", book.getIdAuthor(), "idGenre", book.getIdGenre()));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from book where id_book = :idBook", Map.of("idBook", id));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book set title = :title, id_author = :idAuthor, id_genre = :idGenre where id_book = :idBook",
                Map.of("title", book.getTitle(), "idAuthor", book.getIdAuthor(), "idGenre", book.getIdGenre(), "idBook", book.getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select id_book, title, id_author, id_genre from book where id_book = :idBook", Map.of("idBook", id), new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_book");
            String title = resultSet.getString("title");
            long idAuthor = resultSet.getLong("id_author");
            long idGenre = resultSet.getLong("id_genre");
            return new Book(id, title, idAuthor, idGenre);
        }
    }
}
