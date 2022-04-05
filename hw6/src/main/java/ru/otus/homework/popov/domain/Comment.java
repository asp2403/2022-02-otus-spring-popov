package ru.otus.homework.popov.domain;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private long id;

    @Column(name = "comment_text", nullable = false)
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book")
    private Book book;

    public Comment(long id, String text, Book book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
