package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final List<Question> questions;

    private final ScoreService scoreService;

    private int questionIndex;

    public TestingServiceImpl(QuestionService questionService, ScoreService scoreService) {
        questions = questionService.loadQuestions();
        this.scoreService = scoreService;
    }

    public void startTest() {
        scoreService.resetScore();
        questionIndex = -1;
    }

    @Override
    public Question getNextQuestion() {
        questionIndex++;
        return getQuestion();
    }

    private Question getQuestion() {
        if (questionIndex < questions.size()) {
            return questions.get(questionIndex);
        } else {
            return null;
        }
    }

    @Override
    public void answerQuestion(int answerIndex) {
        var question = getQuestion();
        var answer = question.getAnswers().get(answerIndex);
        if (answer.isCorrect()) {
            scoreService.addScore();
        }
    }

    @Override
    public int getScore() {
        return scoreService.getScore();
    }
}
