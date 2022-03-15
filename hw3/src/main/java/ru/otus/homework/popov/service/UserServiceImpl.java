package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final AppConfig appConfig;
    private final MessageService messageService;

    public UserServiceImpl(IOService ioService, AppConfig appConfig, MessageService messageService) {
        this.ioService = ioService;
        this.appConfig = appConfig;
        this.messageService = messageService;
    }

    private Optional<String> readValue(String descriptionId) {
        var description = messageService.getMessageFormat(descriptionId, messageService.CMD_EXIT);
        var str = ioService.readNotEmptyString(messageService.PROMPT, description, messageService::getIOErrorMessage);
        if(str.equalsIgnoreCase(Character.toString(messageService.CMD_EXIT))) {
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
        ioService.printlnFormat(messageService.getMessage("MSG_HELLO"), user.getFullName(), appConfig.getScoreToPass());
        return Optional.of(user);
    }
}
