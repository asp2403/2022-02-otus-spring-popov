package ru.otus.homework.popov.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.domain.User;
import ru.otus.homework.popov.service.*;

import java.util.Optional;

@ShellComponent
public class AppCommands {

    private Optional<User> user = Optional.empty();

    private final UserService userService;
    private final TestingService testingService;
    private final TestingResultPrinter testingResultPrinter;
    private final MessagePrinter messagePrinter;
    private final UISettings uiSettings;
    private final IOService ioService;

    public AppCommands(UserService userService, TestingService testingService, TestingResultPrinter testingResultPrinter, MessagePrinter messagePrinter, UISettings uiSettings, IOService ioService) {
        this.userService = userService;
        this.testingService = testingService;
        this.testingResultPrinter = testingResultPrinter;
        this.messagePrinter = messagePrinter;
        this.uiSettings = uiSettings;
        this.ioService = ioService;
    }

    @ShellMethod(value = "Register User", key = {"r", "register"})
    public void registerUser() {
        sayHello();
        user = userService.register();
        if (user.isEmpty()) {
            sayGoodBy();
        }

    }

    @ShellMethod(value = "Test User", key = {"t", "test"})
    @ShellMethodAvailability(value = "isTestUserAvailable")
    public void testUser() {
        runTest(user.get());
        sayGoodBy();
    }

    private Availability isTestUserAvailable() {
        return user.isEmpty()? Availability.unavailable("User is not logged in"): Availability.available();
    }

    private void runTest(User user) {
        var testingResult = testingService.testUser(user);
        if (testingResult.isAborted()) {
            return;
        }
        testingResultPrinter.print(testingResult);

    }

    private void sayHello() {
        messagePrinter.println("MSG_WELCOME");
    }

    private void sayGoodBy() {
        messagePrinter.println("MSG_GOOD_BY");
    }

}
