package ru.otus.homework.popov.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.popov.domain.TestingResult;
import ru.otus.homework.popov.domain.User;
import ru.otus.homework.popov.service.TestingService;
import ru.otus.homework.popov.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AppCommandsTest {

    @Autowired
    private Shell shell;

    @MockBean
    private UserService userService;

    @MockBean
    private TestingService testingService;

    private Optional<User> user;

    @DisplayName("должен возвращать CommandNotCurrentlyAvailable если пользователь не зарегистрировался перед выполнением команды test")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserHasNotLogin() {
        Object res =  shell.evaluate(() -> "t");
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("должен возвращать CommandNotCurrentlyAvailable если пользователь прервал регистрацию перед командой test")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserAbortedRegistration() {
        Optional<User> user = Optional.empty();
        given(userService.register()).willReturn(user);
        shell.evaluate(() -> "r");
        verify(userService, times(1)).register();
        Object res = shell.evaluate(() -> "t");
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("должен корректно отрабатывать команду register")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldCorrectRunRegisterCommand() {
        var user = Optional.of(new User("Vasya", "Pupkin"));
        given(userService.register()).willReturn(user);

        shell.evaluate(() -> "r");
        verify(userService, times(1)).register();

        shell.evaluate(() -> "register");
        verify(userService, times(2)).register();
    }

    @DisplayName("должен корректно отрабатывать команду test")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldCorrectRunTestCommand() {
        var user = Optional.of(new User("Vasya", "Pupkin"));
        var testingResult = new TestingResult(user.get());
        given(userService.register()).willReturn(user);
        given(testingService.testUser(eq(user.get()))).willReturn(testingResult);

        shell.evaluate(() -> "register");
        verify(userService, times(1)).register();

        shell.evaluate(() -> "test");
        verify(testingService, times(1)).testUser(user.get());

        shell.evaluate(() -> "t");
        verify(testingService, times(2)).testUser(user.get());
    }

}