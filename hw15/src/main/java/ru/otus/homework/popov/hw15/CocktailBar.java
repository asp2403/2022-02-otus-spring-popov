package ru.otus.homework.popov.hw15;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.popov.hw15.domain.Cocktail;

import java.util.Optional;

@MessagingGateway
public interface CocktailBar {
    @Gateway(requestChannel = "cocktailBar.input", replyChannel = "cocktailBar.output")
    Optional<Cocktail> prepare(String cocktailName);
}
