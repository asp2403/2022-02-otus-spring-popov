package ru.otus.homework.popov.hw5.service.localization;

public interface MessageService {
    String getMessage(String id);
    String getMessage(String id, Object[] args);
}
