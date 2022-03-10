package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AppRunner {
    private final TestingService testingService;
    private final IOService ioService;
    private final AppConfig appConfig;
    private final TestingResultPrinter testingResultPrinter;

    public AppRunner(
            AppConfig appConfig,
            IOService ioService,
            TestingService testingService,
            TestingResultPrinter testingResultPrinter) {
        this.ioService = ioService;
        this.testingService = testingService;
        this.appConfig = appConfig;
        this.testingResultPrinter = testingResultPrinter;
    }

    public void execute() {
        sayHello();
        var user = registerUser();
        user.ifPresent(this::run);
        sayGoodBy();
    }

    private void run(User user) {
        var isTerminated = new AtomicBoolean(false);
        do {
            runTest(user, isTerminated);
            if (isTerminated.get()) {
                return;
            }
            if (!wantTryAgain()) {
                return;
            }
        } while (true);
    }

    private void runTest(User user, AtomicBoolean isTerminated) {
        var testingResult = testingService.testUser(user, isTerminated);
        if (!isTerminated.get()) {
            testingResultPrinter.print(testingResult);
        }
    }


    private void sayGoodBy() {
        ioService.println(Messages.MSG_GOOD_BY);
    }

    private boolean wantTryAgain() {
        ioService.println(Messages.MSG_TRY_AGAIN);
        var s = ioService.readString(Messages.PROMPT);
        return s.equalsIgnoreCase(Character.toString(Messages.CMD_YES));
    }

    private Optional<User> registerUser() {

        var name =  ioService.readNotEmptyString(Messages.PROMPT, Messages.MSG_ENTER_NAME, Messages::getIOErrorMessage);
        if (name.equalsIgnoreCase(Character.toString(Messages.CMD_EXIT))) {
            return Optional.empty();
        }

        var surname = ioService.readNotEmptyString(Messages.PROMPT, Messages.MSG_ENTER_SURNAME, Messages::getIOErrorMessage);
        if (name.equalsIgnoreCase(Character.toString(Messages.CMD_EXIT))) {
            return Optional.empty();
        }

        var user = new User(name, surname);
        ioService.printlnFormat(Messages.MSG_HELLO, user.getFullName(), appConfig.getScoreToPass());
        return Optional.of(user);
    }

    private void sayHello() {
        ioService.println(Messages.MSG_WELCOME);
    }
}
