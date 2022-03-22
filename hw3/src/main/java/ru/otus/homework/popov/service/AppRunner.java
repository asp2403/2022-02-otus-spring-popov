package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.domain.User;


@Service
public class AppRunner {
    private final TestingService testingService;
    private final IOService ioService;
    private final TestingResultPrinter testingResultPrinter;
    private final UserService userService;
    private final MessagePrinter messagePrinter;
    private final UISettings uiSettings;

    public AppRunner(
            IOService ioService,
            TestingService testingService,
            TestingResultPrinter testingResultPrinter,
            UserService userService,
            MessagePrinter messagePrinter,
            UISettings uiSettings) {
        this.ioService = ioService;
        this.testingService = testingService;
        this.testingResultPrinter = testingResultPrinter;
        this.userService = userService;
        this.messagePrinter = messagePrinter;
        this.uiSettings = uiSettings;
    }

    public void executeApp() {
        sayHello();
        var user = userService.register();
        user.ifPresent(this::runTest);
        sayGoodBy();
    }

    private void runTest(User user) {
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

        messagePrinter.println("MSG_GOOD_BY");
    }

    private boolean wantExit() {
        messagePrinter.printlnFormat("MSG_TRY_AGAIN", uiSettings.getCmdYes());
        var s = ioService.readString(uiSettings.getPrompt());
        return !s.equalsIgnoreCase(Character.toString(uiSettings.getCmdYes()));
    }

    private void sayHello() {
        messagePrinter.println("MSG_WELCOME");
    }
}
