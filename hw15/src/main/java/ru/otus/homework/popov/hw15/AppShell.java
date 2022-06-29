package ru.otus.homework.popov.hw15;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.hw15.service.AppCommands;

@ShellComponent
public class AppShell {
    private final AppCommands appCommands;

    public AppShell(AppCommands appCommands) {
        this.appCommands = appCommands;
    }

    @ShellMethod(value = "Get Cocktail", key = {"get-cocktail", "gc"})
    public void getCocktail(String cocktailName) {
        appCommands.getCocktail(cocktailName);
    }

}
