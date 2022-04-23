package ru.otus.homework.popov.service.command;

public interface CommentCommands {
    String getComments(String idBook);
    String addComment(String idBook, String text);
    String getComment(String idComment);
    String updateComment(String idComment, String text);
    String deleteComment(String idComment);
}
