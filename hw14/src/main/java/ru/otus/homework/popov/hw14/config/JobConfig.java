package ru.otus.homework.popov.hw14.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.popov.hw14.domain.Author;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class JobConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

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
                .build();
    }

    @Bean
    public Step loadAuthorsStep() {
        return stepBuilderFactory.get("loadAuthorsStep")
                .<Author, Author>chunk(10)
                .reader(authorReader(dataSource))
                .writer(authorWriter())
                .build();
    }

    @Bean
    public Job importLibrary() {
        return jobBuilderFactory.get("importLibraryJob")
                .start(loadAuthorsStep())
                .build();
    }



    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_author");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }


}
