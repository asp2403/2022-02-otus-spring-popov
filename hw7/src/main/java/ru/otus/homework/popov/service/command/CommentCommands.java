package ru.otus.homework.popov.service.command;

public interface CommentCommands {
    String getComments(long idBook);
    String addComment(long idBook, String text);
    String getComment(long idComment);
    String updateComment(long idComment, String text);
    String deleteComment(long idComment);
}
