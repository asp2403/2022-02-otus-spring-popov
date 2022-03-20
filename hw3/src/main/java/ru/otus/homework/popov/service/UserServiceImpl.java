package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final MessagePrinter messagePrinter;
    private final TestingSettings testingSettings;
    private final UISettings uiSettings;
    private final MessageService messageService;
    private final IOErrorMessageService ioErrorMessageService;

    public UserServiceImpl(IOService ioService,
                           TestingSettings testingSettings,
                           MessagePrinter messagePrinter,
                           UISettings uiSettings,
                           MessageService messageService,
                           IOErrorMessageService ioErrorMessageService) {
        this.ioService = ioService;
        this.testingSettings = testingSettings;
        this.messagePrinter = messagePrinter;
        this.uiSettings = uiSettings;
        this.messageService = messageService;
        this.ioErrorMessageService = ioErrorMessageService;
    }

    private Optional<String> readValue(String descriptionId) {
        var description = messageService.getMessageFormat(descriptionId, uiSettings.getCmdQuit());
        var str = ioService.readNotEmptyString(uiSettings.getPrompt(), description, ioErrorMessageService::getIOErrorMessage);
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
        messagePrinter.printlnFormat("MSG_HELLO", user.getFullName(), testingSettings.getScoreToPass());
        return Optional.of(user);
    }
}
