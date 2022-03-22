package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;

@Service
public class MessagePrinterImpl implements MessagePrinter {
    private final IOService ioService;
    private final MessageService messageService;

    public MessagePrinterImpl(IOService ioService, MessageService messageService) {
        this.ioService = ioService;
        this.messageService = messageService;
    }

    @Override
    public void println(String MessageId) {
        ioService.println(messageService.getMessage(MessageId));
    }

    @Override
    public void printlnFormat(String MessageId, Object... args) {
        ioService.println(messageService.getMessageFormat(MessageId, args));
    }
}
