package ru.otus.homework.popov.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.opencsv.CSVReader;
import ru.otus.homework.popov.exceptions.QuestionsLoadingException;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final String resourceName;

    public QuestionDaoImpl(@Value("${resourceName}") String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Question> loadQuestions() {
        var list = new ArrayList<Question>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceName))))) {
            List<String[]> allRows = reader.readAll();
            for (var strArray : allRows) {
                if (strArray.length >= 3 && (strArray.length % 2 == 1)) {
                    var answers = new ArrayList<Answer>();
                    var i = 1;
                    do {
                        answers.add(new Answer(strArray[i], strArray[i + 1].equals("1")));
                        i+= 2;
                    } while (i < strArray.length);
                    list.add(new Question(strArray[0], answers));
                }
            }
        } catch (Exception e) {
            throw new QuestionsLoadingException("Question loading exception!");
        }
        return list;
    }
}
