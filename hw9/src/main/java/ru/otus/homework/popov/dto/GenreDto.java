package ru.otus.homework.popov.dto;

import ru.otus.homework.popov.domain.Genre;

public class GenreDto {
    private String id;

    public GenreDto() {
    }

    public GenreDto(String id) {
        this.id = id;
    }

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
