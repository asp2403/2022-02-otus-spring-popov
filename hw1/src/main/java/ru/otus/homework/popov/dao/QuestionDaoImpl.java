package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Question;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class QuestionDaoImpl implements QuestionDao {

    private final String resourceName;

    public QuestionDaoImpl(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Question> getQuestions() {
        var list = new ArrayList<Question>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resourceName)))) {
            List<String[]> allRows = reader.readAll();
            for (var strArray : allRows) {
                if (strArray.length >= 1) {
                    var answers = new ArrayList<String>();
                    for (var j = 1; j < strArray.length; j++) {
                        answers.add(strArray[j]);
                    }
                    list.add(new Question(strArray[0], answers));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
