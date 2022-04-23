package ru.otus.homework.popov.service.command;

public interface BookCommands {
    String getAll();
    String insertBook(String title, String idAuthor, String idGenre);
    String updateBook(String idBook, String title, String idAuthor, String idGenre);
    String deleteBook(String idBook);
    String getBookById(String idBook);
}
