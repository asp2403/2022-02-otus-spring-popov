package ru.otus.homework.popov.service.command;

public interface BookCommands {
    String getAll();
    String insertBook(String title, long idAuthor, long idGenre);
    String updateBook(long idBook, String title, long idAuthor, long idGenre);
    String deleteBook(long idBook);
    String getBookById(long idBook);
}
