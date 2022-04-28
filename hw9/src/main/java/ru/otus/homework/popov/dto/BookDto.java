package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Book;

public class BookDto {
    private String id;
    private String title;
    private String authorName;
    private String genreName;
    private int commentCount;

    public BookDto(String id, String title, String authorName, String genreName, int commentCount) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genreName = genreName;
        this.commentCount = commentCount;
    }

    public BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName(), book.getCommentCount());
    }

//    public Book toDomainObject(BookDto bookDto) {
//        var author =
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
