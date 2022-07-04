package ru.otus.homework.popov.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.web.util.UriComponentsBuilder;


@IntegrationComponentScan
@EnableIntegration
@SpringBootApplication
public class Hw15Application {

   public static void main(String[] args) {
        SpringApplication.run(Hw15Application.class, args);
    }

}
