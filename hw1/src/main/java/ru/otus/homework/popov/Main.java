package ru.otus.homework.popov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.service.AppRunner;
import ru.otus.homework.popov.service.IOService;
import ru.otus.homework.popov.service.QuestionConverter;
import ru.otus.homework.popov.service.QuestionService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        IOService ioService = context.getBean(IOService.class);
        QuestionConverter converter = context.getBean(QuestionConverter.class);
        AppRunner runner = context.getBean(AppRunner.class);
        runner.run();
    }
}
