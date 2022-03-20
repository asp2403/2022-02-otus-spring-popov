package ru.otus.homework.popov.service;

public interface MessageService {

    String getMessage(String id);

    String getMessageFormat(String id, Object... args);

}
