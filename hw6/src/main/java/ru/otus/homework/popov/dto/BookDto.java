package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Book;

public class BookDto {
    private final long id;
    private final String title;
    private final String authorName;
    private final String genreName;
    private final long commentCount;

    public BookDto(long id, String title, String authorName, String genreName, long commentCount) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genreName = genreName;
        this.commentCount = commentCount;
    }

    public BookDto(Book book, long commentCount) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthor().getName();
        this.genreName = book.getGenre().getName();
        this.commentCount = commentCount;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public long getCommentCount() {
        return commentCount;
    }
}
