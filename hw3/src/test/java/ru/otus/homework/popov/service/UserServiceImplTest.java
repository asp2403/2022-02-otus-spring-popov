package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.domain.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private TestingSettings testingSettings;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private UserServiceImpl userService;

    private final String name = "Vasya";
    private final String surname = "Pupkin";
    private final String msgEnterName = "Enter your name";
    private final String msgEnterSurname = "Enter your surname";


    @DisplayName("должен корректно регистрировать пользователя")
    @Test
    void shouldCorrectRegister() {
        var userExpected = new User(name, surname);
        given(messageService.getMessageFormat(eq("MSG_ENTER_NAME"), any())).willReturn(msgEnterName);
        given(messageService.getMessageFormat(eq("MSG_ENTER_SURNAME"), any())).willReturn(msgEnterSurname);
        given(ioService.readNotEmptyString(any(), eq(msgEnterName), any())).willReturn(name);
        given(ioService.readNotEmptyString(any(), eq(msgEnterSurname), any())).willReturn(surname);
        given(testingSettings.getScoreToPass()).willReturn(3);
        var user = userService.register();
        verify(ioService, times(1)).readNotEmptyString(any(), eq(msgEnterName), any());
        verify(ioService, times(1)).readNotEmptyString(any(), eq(msgEnterSurname), any());
        assertTrue(user.isPresent());
        assertEquals(userExpected, user.get());
    }

    @DisplayName("должен корректно обрабатывать выход")
    @Test
    void shouldCorrectExit() {
        given(messageService.getMessageFormat(eq("MSG_ENTER_NAME"), any())).willReturn(msgEnterName);
        given(messageService.getMessageFormat(eq("MSG_ENTER_SURNAME"), any())).willReturn(msgEnterSurname);
        given(ioService.readNotEmptyString(any(), eq(msgEnterName), any())).willReturn(name);
        given(ioService.readNotEmptyString(any(), eq(msgEnterSurname), any())).willReturn("q");
        var user = userService.register();
        verify(ioService, times(1)).readNotEmptyString(any(), eq(msgEnterName), any());
        verify(ioService, times(1)).readNotEmptyString(any(), eq(msgEnterSurname), any());
        assertFalse(user.isPresent());
    }
}