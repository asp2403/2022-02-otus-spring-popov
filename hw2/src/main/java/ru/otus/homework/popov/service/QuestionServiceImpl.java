package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getQuestions() {
        return dao.getQuestions();
    }
}
