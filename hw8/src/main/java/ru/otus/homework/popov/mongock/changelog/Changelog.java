package ru.otus.homework.popov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;

@ChangeLog
public class Changelog {

    @ChangeSet(order = "001", id = "dropDb", author = "apopov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "apopov")
    public void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("1", "Лев Толстой"));
        repository.save(new Author("2", "Стивен Кинг"));
        repository.save(new Author("3", "Дж. Р.Р. Толкин"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "apopov")
    public void insertGenres(GenreRepository repository) {
        repository.save(new Genre("1", "Русская классика"));
        repository.save(new Genre("2", "Фэнтези"));
        repository.save(new Genre("3", "Ужасы"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "apopov")
    public void insertBooks(BookRepository bookRepository) {
        var book1 = new Book("1", "Война и мир", new Author("1", "Лев Толстой"), new Genre("1", "Русская классика"));
        var book2 = new Book("2", "Кладбище домашних животных", new Author("2", "Стивен Кинг"), new Genre("3", "Ужасы"));
        var book3 = new Book("3", "Властелин колец", new Author("3", "Дж. Р.Р. Толкин"), new Genre("2", "Фэнтези"));
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }


    @ChangeSet(order = "005", id = "insertComments", author = "apopov")
    public void insertComments(CommentRepository commentRepository, BookRepository bookRepository) {
        var book1 = bookRepository.findById("1");
        book1.ifPresent(
                b -> {
                    var comment1 = new Comment("1", "Восхитительно!");
                    var comment2 = new Comment("2", "Многабукв. Ниасилил...");
                    b.addComment(comment1);
                    b.addComment(comment2);
                    commentRepository.save(comment1);
                    commentRepository.save(comment2);
                    bookRepository.save(b);
                }
        );
        var book2 = bookRepository.findById("2");
        book2.ifPresent(
                b -> {
                    var comment3 = new Comment("3", "Кису жалко");
                    b.addComment(comment3);
                    commentRepository.save(comment3);
                    bookRepository.save(b);
                }
        );
        var book3 = bookRepository.findById("3");
        book3.ifPresent(
                b -> {
                    var comment4 = new Comment("4", "Надо было лететь на орлах!");
                    b.addComment(comment4);
                    commentRepository.save(comment4);
                    bookRepository.save(b);
                }
        );
    }
}