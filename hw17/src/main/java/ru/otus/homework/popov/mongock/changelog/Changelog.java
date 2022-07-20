package ru.otus.homework.popov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.homework.popov.domain.*;
import ru.otus.homework.popov.repository.*;

import java.util.Arrays;

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
                    var comment1 = new Comment("1", "Восхитительно!", "Изя Шниперсон");
                    var comment2 = new Comment("2", "Многабукв. Ниасилил...", "Вася Пупкин");
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
                    var comment3 = new Comment("3", "Кису жалко", "Эдуард Суровый");
                    b.addComment(comment3);
                    commentRepository.save(comment3);
                    bookRepository.save(b);
                }
        );
        var book3 = bookRepository.findById("3");
        book3.ifPresent(
                b -> {
                    var comment4 = new Comment("4", "Надо было лететь на орлах!", "Вася Пупкин");
                    b.addComment(comment4);
                    commentRepository.save(comment4);
                    bookRepository.save(b);
                }
        );
    }

    @ChangeSet(order = "006", id = "insertUsers", author = "apopov")
    public void insertUsers(UserRepository repository) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var admin = new User("1", "admin", passwordEncoder.encode( "123"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")), "Иван", "Петров");
        var user1 = new User("2", "vpupkin", passwordEncoder.encode( "vp"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "Вася", "Пупкин");
        var user2 = new User("3", "ishnip", passwordEncoder.encode( "ish"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "Изя", "Шниперсон");
        var moder = new User("4", "esurov", passwordEncoder.encode( "es"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MODERATOR")), "Эдуард", "Суровый");
        repository.save(admin);
        repository.save(user1);
        repository.save(user2);
        repository.save(moder);
    }


}