package ru.otus.homework.popov.hw14.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.popov.hw14.domain.Author;
import ru.otus.homework.popov.hw14.domain.Book;
import ru.otus.homework.popov.hw14.domain.Comment;
import ru.otus.homework.popov.hw14.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
class JobConfigTest {

    public static final String JOB_NAME = "importLibraryJob";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("Импортирование библиотеки должно работать правильно")
    @Test
    void importLibraryShouldWork() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JOB_NAME);


        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        var actualAuthor = mongoTemplate.findById("1", Author.class, "authors");
        var actualGenre = mongoTemplate.findById("1", Genre.class, "genres");
        var actualBook = mongoTemplate.findById("1", Book.class, "books");
        var actualComment = mongoTemplate.findById("1", Comment.class, "comments");

        var tolstoi = new Author("1", "Лев Толстой");
        var rusClassics = new Genre("1", "Русская классика");
        var voinaIMir = new Book("1", "Война и мир", tolstoi, rusClassics, 2);
        var voshititelno = new Comment("1", "Восхитительно!", voinaIMir);

        assertThat(tolstoi).usingRecursiveComparison().isEqualTo(actualAuthor);
        assertThat(rusClassics).usingRecursiveComparison().isEqualTo(actualGenre);
        assertThat(voinaIMir).usingRecursiveComparison().isEqualTo(actualBook);
        assertThat(voshititelno).usingRecursiveComparison().isEqualTo(actualComment);

    }
}