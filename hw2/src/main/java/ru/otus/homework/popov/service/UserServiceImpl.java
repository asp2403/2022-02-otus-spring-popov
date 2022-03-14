package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final IOService ioService;
    private final AppConfig appConfig;

    public UserServiceImpl(IOService ioService, AppConfig appConfig) {
        this.ioService = ioService;
        this.appConfig = appConfig;
    }

    private Optional<String> readValue(String description) {
        var str = ioService.readNotEmptyString(Messages.PROMPT, description, Messages::getIOErrorMessage);
        if(str.equalsIgnoreCase(Character.toString(Messages.CMD_EXIT))) {
            return Optional.empty();
        } else {
            return Optional.of(str);
        }
    }


    @Override
    public Optional<User> register() {
        var name = readValue(Messages.MSG_ENTER_NAME);
        if (name.isEmpty()) {
            return Optional.empty();
        }

        var surname = readValue(Messages.MSG_ENTER_SURNAME);
        if (surname.isEmpty()) {
            return Optional.empty();
        }

        var user = new User(name.get(), surname.get());
        ioService.printlnFormat(Messages.MSG_HELLO, user.getFullName(), appConfig.getScoreToPass());
        return Optional.of(user);
    }
}
