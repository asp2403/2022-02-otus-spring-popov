package ru.otus.homework.popov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;

import java.sql.SQLException;

@SpringBootApplication
public class Hw7Application {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Hw7Application.class, args);
		//Console.main(args);
	}

}
