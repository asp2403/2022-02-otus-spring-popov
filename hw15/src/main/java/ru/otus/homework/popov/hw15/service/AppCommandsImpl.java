package ru.otus.homework.popov.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.CocktailBar;

@Service
public class AppCommandsImpl implements AppCommands {
    private final CocktailBar cocktailBar;

    public AppCommandsImpl(CocktailBar cocktailBar) {
        this.cocktailBar = cocktailBar;
    }

    @Override
    public void getCocktail(String cocktailName) {
        cocktailBar.prepare(cocktailName);

    }
}
