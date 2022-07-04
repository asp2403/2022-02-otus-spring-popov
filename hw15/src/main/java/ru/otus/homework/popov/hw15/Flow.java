package ru.otus.homework.popov.hw15;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.service.BarService;
import ru.otus.homework.popov.hw15.service.ResponseProcessService;

@Service
public class Flow {
    private static final String URL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s={cocktail}";

    private final ResponseProcessService responseProcessService;
    private final BarService barService;

    public Flow(ResponseProcessService responseProcessService, BarService barService) {
        this.responseProcessService = responseProcessService;
        this.barService = barService;
    }

    @Bean
    public IntegrationFlow cocktailBarFlow() {
        return IntegrationFlows.from("cocktailBar.input")
                .handle(Http.outboundGateway(URL)
                        .httpMethod(HttpMethod.GET)
                        .uriVariable("cocktail",
                                m -> m.getPayload()
                        )
                        .expectedResponseType(String.class)
                )
                .handle(responseProcessService, "process")
                .handle(barService, "prepare")
                .channel("cocktailBar.output")
                .get();
    }
}
