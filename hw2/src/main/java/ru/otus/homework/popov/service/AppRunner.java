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
    private final UserService userService;

    public AppRunner(
            AppConfig appConfig,
            IOService ioService,
            TestingService testingService,
            TestingResultPrinter testingResultPrinter,
            UserService userService) {
        this.ioService = ioService;
        this.testingService = testingService;
        this.appConfig = appConfig;
        this.testingResultPrinter = testingResultPrinter;
        this.userService = userService;
    }

    public void execute() {
        sayHello();
        var user = userService.register();
        user.ifPresent(this::run);
        sayGoodBy();
    }

    private void run(User user) {
        do {
            var testingResult = testingService.testUser(user);
            if (testingResult.isAborted()) {
                return;
            }
            testingResultPrinter.print(testingResult);
            if (wantExit()) {
                return;
            }
        } while (true);
    }

    private void sayGoodBy() {
        ioService.println(Messages.MSG_GOOD_BY);
    }

    private boolean wantExit() {
        ioService.println(Messages.MSG_TRY_AGAIN);
        var s = ioService.readString(Messages.PROMPT);
        return !s.equalsIgnoreCase(Character.toString(Messages.CMD_YES));
    }

    private void sayHello() {
        ioService.println(Messages.MSG_WELCOME);
    }
}
