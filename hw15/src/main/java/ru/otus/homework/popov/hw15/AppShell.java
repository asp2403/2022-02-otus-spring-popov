package ru.otus.homework.popov.hw15;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.hw15.service.AppService;

@ShellComponent
public class AppShell {
    private final AppService appService;

    public AppShell(AppService appService) {
        this.appService = appService;
    }

    @ShellMethod(value = "Get Cocktail", key = {"get-cocktail", "gc"})
    public String getCocktail(String cocktailName) {
        return appService.getCocktail(cocktailName);
    }

}
