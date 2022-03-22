package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;

@Service
public class IOErrorMessageServiceImpl implements IOErrorMessageService{

    private final MessageService messageService;

    public IOErrorMessageServiceImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String getIOErrorMessage(int errorCode) {
        switch (errorCode) {
            case IOService.ERR_BLANK_STRING:
                return messageService.getMessage("ERR_EMPTY_STRING");
            case IOService.ERR_CHAR_EXPECTED:
                return messageService.getMessage("ERR_ONE_CHAR_EXPECTED");
            default:
                return messageService.getMessage("ERR_UNKNOWN");
        }
    }
}
