package ru.otus.homework.popov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.service.QuestionService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        List<Question> list = service.getQuestions();
        for (var i = 0; i < list.size(); i ++) {
            var question = list.get(i);
            System.out.println((i + 1) + ". " + question.getBody());
            var answers = question.getAnswers();
            for (var j = 0; j < answers.size(); j++) {
                System.out.println("   " + (char) (97 + j) + ". " + answers.get(j));
            }
        }
    }
}
