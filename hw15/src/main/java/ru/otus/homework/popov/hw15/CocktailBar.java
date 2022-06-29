package ru.otus.homework.popov.hw15;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.popov.hw15.domain.Cocktail;

@MessagingGateway
public interface CocktailBar {
    @Gateway(requestChannel = "cocktailBar.input", replyChannel = "cocktailBar.output")
    Cocktail prepare(String cocktailName);
}
