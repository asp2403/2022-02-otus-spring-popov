package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Book;

import javax.validation.constraints.NotBlank;




public class BookDto {

    private String id;

    @NotBlank(message="Название не должно быть пустым")
    private String title;
    private Author author;
    private Genre genre;

    public static class Author {
        private String id;

        public Author(String id) {
            this.id = id;
        }

        public Author() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Genre {
        private String id;

        public Genre(String id) {
            this.id = id;
        }

        public Genre() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public BookDto() {}

    public static BookDto fromDomainObject(Book book) {
        var dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(new Author(book.getAuthor().getId()));
        dto.setGenre(new Genre(book.getGenre().getId()));
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
