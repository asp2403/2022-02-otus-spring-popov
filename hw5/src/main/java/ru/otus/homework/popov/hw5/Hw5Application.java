package ru.otus.homework.popov.hw5;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Console;

@SpringBootApplication
public class Hw5Application {

	public static void main(String[] args) throws Exception{

		SpringApplication.run(Hw5Application.class, args);
		Console.main(args);
	}

}
