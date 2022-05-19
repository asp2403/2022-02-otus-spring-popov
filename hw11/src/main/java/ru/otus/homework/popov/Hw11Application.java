package ru.otus.homework.popov;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class Hw11Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Hw11Application.class, args);

		var dbInitializer = context.getBean(DBInitializer.class);
		dbInitializer.init();

	}

}
