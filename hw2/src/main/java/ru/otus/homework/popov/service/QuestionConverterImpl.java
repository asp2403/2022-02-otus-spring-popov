package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Question;

@Service
public class QuestionConverterImpl implements QuestionConverter {
    @Override
    public String convertQuestionToString(Question question) {
        var sb = new StringBuilder();
        sb.append(question.getIndex() + 1).append(". ").append(question.getBody()).append("\n");
        var answers = question.getAnswers();
        for (var i = 0; i < answers.size(); i++) {
            var answer = answers.get(i);
            sb.append("   ").append((char) (97 + i)).append(". ").append(answer.getText()).append("\n");
        }
        return sb.toString();
    }
}
