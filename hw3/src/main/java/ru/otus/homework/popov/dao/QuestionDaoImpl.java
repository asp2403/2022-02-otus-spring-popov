package ru.otus.homework.popov.dao;

import com.opencsv.CSVReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.config.LocalizationSettings;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.exceptions.QuestionsLoadingException;
import ru.otus.homework.popov.service.LocaleProvider;
import ru.otus.homework.popov.service.QuestionResourceNameProvider;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final String resourceName;
    private final ResourceLoader resourceLoader;

    public QuestionDaoImpl(
            LocalizationSettings localizationSettings,
            LocaleProvider localeProvider,
            ResourceLoader resourceLoader,
            QuestionResourceNameProvider questionResourceNameProvider) {
        var baseName = localizationSettings.getQuestionsBaseName();
        resourceName = questionResourceNameProvider.getResourceName(baseName, localeProvider.getLocale());
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Question> loadQuestions() {
        var list = new ArrayList<Question>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(resourceLoader.getResource(resourceName).getInputStream()))) {
            List<String[]> allRows = reader.readAll();
            for (var index = 0; index < allRows.size(); index ++) {
                var strArray = allRows.get(index);
                if (strArray.length >= 3 && (strArray.length % 2 == 1)) {
                    var answers = new ArrayList<Answer>();
                    var i = 1;
                    do {
                        answers.add(new Answer(strArray[i], strArray[i + 1].equals("1")));
                        i+= 2;
                    } while (i < strArray.length);
                    list.add(new Question(index, strArray[0], answers));
                }
            }
        } catch (Exception e) {
            throw new QuestionsLoadingException("Question loading exception!");
        }
        return list;
    }
}
