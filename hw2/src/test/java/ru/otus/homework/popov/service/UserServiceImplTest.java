package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.popov.domain.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private UserServiceImpl userService;

    private String name = "Vasya";
    private String surname = "Pupkin";

    @DisplayName("должен корректно регистрировать пользователя")
    @Test
    void shouldCorrectRegister() {
        var userExpected = new User(name, surname);
        given(ioService.readNotEmptyString(any(), eq(Messages.MSG_ENTER_NAME), any())).willReturn(name);
        given(ioService.readNotEmptyString(any(), eq(Messages.MSG_ENTER_SURNAME), any())).willReturn(surname);
        given(appConfig.getScoreToPass()).willReturn(3);
        var user = userService.register();
        verify(ioService, times(1)).readNotEmptyString(any(), eq(Messages.MSG_ENTER_NAME), any());
        verify(ioService, times(1)).readNotEmptyString(any(), eq(Messages.MSG_ENTER_SURNAME), any());
        assertTrue(user.isPresent());
        assertEquals(userExpected, user.get());
    }

    @DisplayName("должен корректно обрабатывать выход")
    @Test
    void shouldCorrectExit() {
        given(ioService.readNotEmptyString(any(), eq(Messages.MSG_ENTER_NAME), any())).willReturn(name);
        given(ioService.readNotEmptyString(any(), eq(Messages.MSG_ENTER_SURNAME), any())).willReturn("q");
        var user = userService.register();
        verify(ioService, times(1)).readNotEmptyString(any(), eq(Messages.MSG_ENTER_NAME), any());
        verify(ioService, times(1)).readNotEmptyString(any(), eq(Messages.MSG_ENTER_SURNAME), any());
        assertFalse(user.isPresent());
    }
}