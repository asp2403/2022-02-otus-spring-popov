package ru.otus.homework.popov.service.localization;

public interface MessageService {
    String getMessage(String id);
    String getMessage(String id, Object[] args);
}
