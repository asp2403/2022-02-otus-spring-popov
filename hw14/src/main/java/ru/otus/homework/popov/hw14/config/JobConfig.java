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
import ru.otus.homework.popov.hw14.domain.mongo.MongoAuthor;
import ru.otus.homework.popov.hw14.domain.mongo.MongoBook;
import ru.otus.homework.popov.hw14.domain.mongo.MongoGenre;
import ru.otus.homework.popov.hw14.domain.rdb.RdbAuthor;
import ru.otus.homework.popov.hw14.domain.rdb.RdbBook;
import ru.otus.homework.popov.hw14.domain.rdb.RdbGenre;

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
    public ItemReader<RdbAuthor> authorReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<RdbAuthor>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("select * from author")
                .rowMapper(new RdbAuthorMapper())
                .build();
    }


    @Bean
    public ItemWriter<MongoAuthor> authorWriter() {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public ItemProcessor<RdbAuthor, MongoAuthor> transformAuthor() {
        return rdbAuthor -> new MongoAuthor(String.valueOf(rdbAuthor.getId()), rdbAuthor.getName());
    }

    @Bean
    public Step importAuthorsStep() {
        return stepBuilderFactory.get("importAuthorsStep")
                .<RdbAuthor, MongoAuthor>chunk(CHUNK_SIZE)
                .reader(authorReader(dataSource))
                .processor(transformAuthor())
                .writer(authorWriter())
                .listener(new ItemReadListener<RdbAuthor>() {


                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(RdbAuthor rdbAuthor) {
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
    public ItemReader<RdbGenre> genreReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<RdbGenre>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("select * from genre")
                .rowMapper(new RdbGenreMapper())
                .build();
    }


    @Bean
    public ItemWriter<MongoGenre> genreWriter() {
        return new MongoItemWriterBuilder<MongoGenre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public ItemProcessor<RdbGenre, MongoGenre> transformGenre() {
        return rdbGenre -> new MongoGenre(String.valueOf(rdbGenre.getId()), rdbGenre.getName());
    }

    @Bean
    public Step importGenresStep() {
        return stepBuilderFactory.get("importGenresStep")
                .<RdbGenre, MongoGenre>chunk(CHUNK_SIZE)
                .reader(genreReader(dataSource))
                .processor(transformGenre())
                .writer(genreWriter())
                .listener(new ItemReadListener<RdbGenre>() {

                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(RdbGenre rdbGenre) {
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
    public ItemReader<RdbBook> bookReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<RdbBook>()
                .name("bookReader")
                .dataSource(dataSource)
                .sql("select id_book, title, b.id_author, b.id_genre, a.name as author_name, g.name as genre_name  " +
                        "from book b " +
                        "inner join author a on b.id_author = a.id_author " +
                        "inner join genre g on b.id_genre = g.id_genre ")
                .rowMapper(new RdbBookMapper())
                .build();
    }

    @Bean
    public ItemProcessor<RdbBook, MongoBook> transformBook() {
        return rdbBook -> new MongoBook(String.valueOf(rdbBook.getId()), rdbBook.getTitle(),
                new MongoAuthor(String.valueOf(rdbBook.getAuthor().getId()), rdbBook.getAuthor().getName()),
                new MongoGenre(String.valueOf(rdbBook.getGenre().getId()), rdbBook.getGenre().getName()));
    }

    @Bean
    public ItemWriter<MongoBook> bookWriter() {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Step importBooksStep() {
        return stepBuilderFactory.get("importBooksStep")
                .<RdbBook, MongoBook>chunk(CHUNK_SIZE)
                .reader(bookReader(dataSource))
                .processor(transformBook())
                .writer(bookWriter())
                .listener(new ItemReadListener<RdbBook>() {

                              @Override
                              public void beforeRead() {

                              }

                              @Override
                              public void afterRead(RdbBook rdbBook) {
                                  logger.info(rdbBook.getAuthor().getName() + " \"" + rdbBook.getTitle() + "\" (" + rdbBook.getGenre().getName() + ")");
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


    private static class RdbAuthorMapper implements RowMapper<RdbAuthor> {

        @Override
        public RdbAuthor mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_author");
            String name = resultSet.getString("name");
            return new RdbAuthor(id, name);
        }
    }

    private static class RdbGenreMapper implements RowMapper<RdbGenre> {

        @Override
        public RdbGenre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_genre");
            String name = resultSet.getString("name");
            return new RdbGenre(id, name);
        }
    }

    private static class RdbBookMapper implements RowMapper<RdbBook> {

        @Override
        public RdbBook mapRow(ResultSet resultSet, int i) throws SQLException {
            var idBook = resultSet.getLong("id_book");
            var title = resultSet.getString("title");
            var idAuthor = resultSet.getLong("id_author");
            var authorName = resultSet.getString("author_name");
            var author = new RdbAuthor(idAuthor, authorName);
            var idGenre = resultSet.getLong("id_genre");
            var genreName = resultSet.getString("genre_name");
            var genre = new RdbGenre(idGenre, genreName);
            return new RdbBook(idBook, title, author, genre);
        }
    }


}
