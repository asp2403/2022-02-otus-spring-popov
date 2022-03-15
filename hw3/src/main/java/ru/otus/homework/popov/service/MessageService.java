package ru.otus.homework.popov.service;

public interface MessageService {

    char CMD_EXIT = 'q';
    char CMD_YES = 'y';

    String PROMPT = ">";

    String getMessage(String id);

    String getMessageFormat(String id, Object... args);

    String getIOErrorMessage(int errorCode);

}
