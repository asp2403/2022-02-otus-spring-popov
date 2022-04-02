package ru.otus.homework.popov.hw5.service.command;

public interface InsertBookCommand {
    String execute(String title, long idAuthor, long idGenre);
}
