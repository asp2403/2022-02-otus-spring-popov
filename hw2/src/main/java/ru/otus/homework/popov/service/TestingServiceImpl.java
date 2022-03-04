package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final QuestionDao questionDao;

    private User user;

    private List<Question> questions;

    public TestingServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void registerUser(String userName) {
        user = new User(userName);
        loadQuestions();
    }

    private void loadQuestions() {
        questions = questionDao.loadQuestions();
    }

    @Override
    public void answerQuestion(int questionIndex, int answerIndex) {
        var question = questions.get(questionIndex);
        var answer = question.getAnswers().get(answerIndex);
        if (answer.isCorrect()) {
            user.addScore();
        }
    }

    @Override
    public void tryAgain() {
        user.resetScore();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Question getQuestion(int index) {
        return questions.get(index);
    }

    @Override
    public int getQuestionCount() {
        return questions.size();
    }
}
