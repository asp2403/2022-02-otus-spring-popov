package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Author;

public class AuthorDto {
    private String id;

    public AuthorDto() {}

    public AuthorDto(String id) {
        this.id = id;
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
