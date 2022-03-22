package ru.otus.homework.popov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework.popov.service.AppRunner;

@SpringBootApplication
public class Hw3Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Hw3Application.class, args);
		var runner = context.getBean(AppRunner.class);
		runner.executeApp();
	}

}
