package ru.otus.homework.popov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.popov.config.LocalizationSettings;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.service.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Hw3ApplicationTests {

	@Autowired
	private UISettings uiSettings;

	@Autowired
	private LocalizationSettings localizationSettings;

	@Autowired
	private TestingSettings testingSettings;

	@Autowired
	private AppRunner appRunner;

	@Autowired
	private IOErrorMessageService ioErrorMessageService;

	@Autowired
	private IOProvider ioProvider;

	@Autowired
	private IOService ioService;

	@Autowired
	private LocaleProvider localeProvider;

	@Autowired
	private MessagePrinter messagePrinter;

	@Autowired
	private MessageService messageService;

	@Autowired
	private QuestionConverter questionConverter;

	@Autowired
	private QuestionResourceNameProvider questionResourceNameProvider;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private TestingResultPrinter testingResultPrinter;

	@Autowired
	private TestingService testingService;

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionDao questionDao;




	@DisplayName("должен корректно загружать контекст")
	@Test
	void shouldCorrectLoadContext() {
		assertThat(uiSettings).isNotNull();
		assertThat(localizationSettings).isNotNull();
		assertThat(testingSettings).isNotNull();
		assertThat(appRunner).isNotNull();
		assertThat(ioErrorMessageService).isNotNull();
		assertThat(ioProvider).isNotNull();
		assertThat(ioService).isNotNull();
		assertThat(localeProvider).isNotNull();
		assertThat(messagePrinter).isNotNull();
		assertThat(messageService).isNotNull();
		assertThat(questionConverter).isNotNull();
		assertThat(questionService).isNotNull();
		assertThat(questionResourceNameProvider).isNotNull();
		assertThat(testingResultPrinter).isNotNull();
		assertThat(testingService).isNotNull();
		assertThat(userService).isNotNull();
		assertThat(questionDao).isNotNull();
	}

}
