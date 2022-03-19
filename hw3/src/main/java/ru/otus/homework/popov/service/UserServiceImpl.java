package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final MessageService messageService;
    private final TestingSettings testingSettings;
    private final UISettings uiSettings;

    public UserServiceImpl(IOService ioService, TestingSettings testingSettings, MessageService messageService, UISettings uiSettings) {
        this.ioService = ioService;
        this.testingSettings = testingSettings;
        this.messageService = messageService;
        this.uiSettings = uiSettings;
    }

    private Optional<String> readValue(String descriptionId) {
        var description = messageService.getMessageFormat(descriptionId, uiSettings.getCmdQuit());
        var str = ioService.readNotEmptyString(uiSettings.getPrompt(), description, messageService::getIOErrorMessage);
        if(str.equalsIgnoreCase(Character.toString(uiSettings.getCmdQuit()))) {
            return Optional.empty();
        } else {
            return Optional.of(str);
        }
    }


    @Override
    public Optional<User> register() {
        var name = readValue("MSG_ENTER_NAME");
        if (name.isEmpty()) {
            return Optional.empty();
        }

        var surname = readValue("MSG_ENTER_SURNAME");
        if (surname.isEmpty()) {
            return Optional.empty();
        }

        var user = new User(name.get(), surname.get());
        ioService.printlnFormat(messageService.getMessage("MSG_HELLO"), user.getFullName(), testingSettings.getScoreToPass());
        return Optional.of(user);
    }
}
