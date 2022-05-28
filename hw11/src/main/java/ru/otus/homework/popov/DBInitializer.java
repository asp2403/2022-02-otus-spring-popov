package ru.otus.homework.popov;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;

@Service
public class DBInitializer {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;

    public DBInitializer(AuthorRepository authorRepository, BookRepository bookRepository, CommentRepository commentRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.genreRepository = genreRepository;
    }

    private void insertAuthors(AuthorRepository repository) {
        repository.save(new Author("1", "Лев Толстой")).subscribe();
        repository.save(new Author("2", "Стивен Кинг")).subscribe();
        repository.save(new Author("3", "Дж. Р.Р. Толкин")).subscribe();
    }

    private void insertGenres(GenreRepository repository) {
        repository.save(new Genre("1", "Русская классика")).subscribe();
        repository.save(new Genre("2", "Фэнтези")).subscribe();
        repository.save(new Genre("3", "Ужасы")).subscribe();
    }

    private void insertBooks(BookRepository bookRepository) {
        var book1 = new Book("1", "Война и мир", new Author("1", "Лев Толстой"), new Genre("1", "Русская классика"));
        var book2 = new Book("2", "Кладбище домашних животных", new Author("2", "Стивен Кинг"), new Genre("3", "Ужасы"));
        var book3 = new Book("3", "Властелин колец", new Author("3", "Дж. Р.Р. Толкин"), new Genre("2", "Фэнтези"));
        bookRepository.save(book1).subscribe(
                b -> {
                    var comment1 = new Comment("1", "Восхитительно!");
                    var comment2 = new Comment("2", "Многабукв. Ниасилил...");
                    b.addComment(comment1);
                    b.addComment(comment2);
                    commentRepository.save(comment1).subscribe(
                            c1 -> commentRepository.save(comment2).subscribe(
                                    c2 -> bookRepository.save(b).subscribe()
                            )
                    );
                }
        );
        bookRepository.save(book2).subscribe(
                b -> {
                    var comment3 = new Comment("3", "Кису жалко");
                    b.addComment(comment3);
                    commentRepository.save(comment3).subscribe(
                            (c) -> bookRepository.save(b).subscribe()
                    );
                }
        );
        bookRepository.save(book3).subscribe(
                b -> {
                    var comment4 = new Comment("4", "Надо было лететь на орлах!");
                    b.addComment(comment4);
                    commentRepository.save(comment4).subscribe(
                            c ->  bookRepository.save(b).subscribe()
                    );
                }
        );
    }



    public void init() {
        insertAuthors(authorRepository);
        insertGenres(genreRepository);
        insertBooks(bookRepository);
    }
}
