package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Book;

import javax.validation.constraints.NotBlank;




public class BookDto {

    private String id;

    @NotBlank(message="Название не должно быть пустым")
    private String title;
    private AuthorDto author;
    private GenreDto genre;

    public BookDto() {}

    public static BookDto fromDomainObject(Book book) {
        var dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(AuthorDto.fromDomainObject(book.getAuthor()));
        dto.setGenre(GenreDto.fromDomainObject(book.getGenre()));
        return dto;
    }

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

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public void setGenre(GenreDto genre) {
        this.genre = genre;
    }
}
