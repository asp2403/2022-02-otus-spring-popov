package ru.otus.homework.hw18.bar.service;

import ru.otus.homework.hw18.bar.domain.Cocktail;
import ru.otus.homework.hw18.bar.domain.Order;

import java.util.Optional;

public interface BarService {
    Order getCocktail(String cocktailName);

}
