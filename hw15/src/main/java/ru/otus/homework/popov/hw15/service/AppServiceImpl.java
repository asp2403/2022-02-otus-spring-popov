package ru.otus.homework.popov.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.CocktailBar;

@Service
public class AppServiceImpl implements AppService {
    private final CocktailBar cocktailBar;

    public AppServiceImpl(CocktailBar cocktailBar) {
        this.cocktailBar = cocktailBar;
    }

    @Override
    public String getCocktail(String cocktailName) {
        var cocktail = cocktailBar.prepare(cocktailName);
        return cocktail.map(c -> "Enjoy your '" + c.getReceipt().getCocktailName() + "'!")
                .orElse("Sorry, we couldn't find the receipt of your cocktail. Would you like to order the other one?");
    }
}
