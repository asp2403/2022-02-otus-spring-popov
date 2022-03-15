package ru.otus.homework.popov.dao;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.exceptions.QuestionsLoadingException;
import ru.otus.homework.popov.service.AppConfig;
import ru.otus.homework.popov.service.LocaleProvider;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final String resourceName;

    public QuestionDaoImpl(AppConfig appConfig, LocaleProvider localeProvider) {

        this.resourceName = getResourceName(appConfig.getQuestionsBaseName(), localeProvider.getLocale());
    }

    @Override
    public List<Question> loadQuestions() {
        var list = new ArrayList<Question>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceName))))) {
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

    private String getResourceName(String baseName, Locale locale) {
        var ext = ".csv";
        var separator = "_";
        var language = locale.getLanguage();
        if (language.isEmpty()) {
            return baseName + ext;
        }
        var country = locale.getCountry();
        return baseName + separator + language + separator + country + ext;

    }
}
