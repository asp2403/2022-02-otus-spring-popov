package ru.otus.homework.popov.hw14.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import ru.otus.homework.popov.hw14.domain.Author;
import ru.otus.homework.popov.hw14.domain.Book;
import ru.otus.homework.popov.hw14.domain.Genre;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class JobConfig {
    private static final int CHUNK_SIZE = 10;
    private final Logger logger = LoggerFactory.getLogger("JobLogger");

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public ItemReader<Author> authorReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Author>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("select * from author")
                .rowMapper(new AuthorMapper())
                .build();
    }


    @Bean
    public ItemWriter<Author> authorWriter() {
        return new MongoItemWriterBuilder<Author>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public Step importAuthorsStep() {
        return stepBuilderFactory.get("importAuthorsStep")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader(dataSource))
                .writer(authorWriter())
                .listener(new ItemReadListener<Author>() {


                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(Author rdbAuthor) {
                                  logger.info(rdbAuthor.getName());
                              }

                              @Override
                              public void onReadError(@NonNull Exception e) {
                                  logger.info("Ошибка чтения");
                              }
                          }

                )
                .listener(new StepExecutionListener() {
                              @Override
                              public void beforeStep(StepExecution stepExecution) {
                                  logger.info("Импортируем авторов...");
                              }

                              @Override
                              public ExitStatus afterStep(StepExecution stepExecution) {
                                  return null;
                              }

                          }

                )
                .build();
    }

    @Bean
    public ItemReader<Genre> genreReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Genre>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("select * from genre")
                .rowMapper(new GenreMapper())
                .build();
    }


    @Bean
    public ItemWriter<Genre> genreWriter() {
        return new MongoItemWriterBuilder<Genre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public Step importGenresStep() {
        return stepBuilderFactory.get("importGenresStep")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader(dataSource))
                .writer(genreWriter())
                .listener(new ItemReadListener<Genre>() {

                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(Genre rdbGenre) {
                                  logger.info(rdbGenre.getName());
                              }

                              @Override
                              public void onReadError(@NonNull Exception e) {
                                  logger.info("Ошибка чтения");
                              }
                          }

                )
                .listener(new StepExecutionListener() {
                              @Override
                              public void beforeStep(StepExecution stepExecution) {
                                  logger.info("Импортируем жанры...");
                              }

                              @Override
                              public ExitStatus afterStep(StepExecution stepExecution) {
                                  return null;
                              }

                          }

                )
                .build();
    }

    @Bean
    public ItemReader<Book> bookReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Book>()
                .name("bookReader")
                .dataSource(dataSource)
                .sql("select b.id_book, title, b.id_author, b.id_genre, a.name as author_name, g.name as genre_name, count(c.id_comment) as c_count  " +
                        "from book b " +
                        "inner join author a on b.id_author = a.id_author " +
                        "inner join genre g on b.id_genre = g.id_genre " +
                        "inner join comment c on b.id_book = c.id_book " +
                     "group by b.id_book")
                .rowMapper(new BookMapper())
                .build();
    }

    @Bean
    public ItemWriter<Book> bookWriter() {
        return new MongoItemWriterBuilder<Book>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Step importBooksStep() {
        return stepBuilderFactory.get("importBooksStep")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookReader(dataSource))
                .writer(bookWriter())
                .listener(new ItemReadListener<Book>() {

                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(Book book) {
                                  logger.info(book.getAuthor().getName() + " \"" + book.getTitle() + "\" (" + book.getGenre().getName() + ")");
                              }

                              @Override
                              public void onReadError(@NonNull Exception e) {
                                  logger.info("Ошибка чтения");
                              }
                          }

                )
                .listener(new StepExecutionListener() {
                              @Override
                              public void beforeStep(StepExecution stepExecution) {
                                  logger.info("Импортируем книги...");
                              }

                              @Override
                              public ExitStatus afterStep(StepExecution stepExecution) {
                                  return null;
                              }

                          }

                )
                .build();
    }


    @Bean
    public Job importLibrary() {
        return jobBuilderFactory.get("importLibraryJob")
                .start(importAuthorsStep())
                .next(importGenresStep())
                .next(importBooksStep())
                .build();
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = String.valueOf(resultSet.getLong("id_author"));
            var name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = String.valueOf(resultSet.getLong("id_genre"));
            var name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            var idBook = String.valueOf(resultSet.getLong("id_book"));
            var title = resultSet.getString("title");
            var idAuthor = String.valueOf(resultSet.getLong("id_author"));
            var authorName = resultSet.getString("author_name");
            var author = new Author(idAuthor, authorName);
            var idGenre = String.valueOf(resultSet.getLong("id_genre"));
            var genreName = resultSet.getString("genre_name");
            var genre = new Genre(idGenre, genreName);
            return new Book(idBook, title, author, genre, resultSet.getInt("c_count"));
        }
    }


}
