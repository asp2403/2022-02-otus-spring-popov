package ru.otus.homework.hw18.bar.service;

import ru.otus.homework.hw18.bar.domain.Cocktail;

import java.util.Optional;

public interface BarService {
    Optional<Cocktail> prepareCocktail(String cocktailName);
}
