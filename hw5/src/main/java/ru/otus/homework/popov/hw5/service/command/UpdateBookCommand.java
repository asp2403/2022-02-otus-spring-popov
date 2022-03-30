package ru.otus.homework.popov.hw5.service.command;

public interface UpdateBookCommand {
    String execute(long idBook, String title, long idAuthor, long idGenre);
}
