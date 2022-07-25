package ru.otus.homework.hw18.bar.service;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.otus.homework.hw18.bar.domain.Cocktail;
import ru.otus.homework.hw18.bar.domain.Order;
import ru.otus.homework.hw18.bar.feign.RecipeProxy;

import java.util.Optional;

@Service
public class BarServiceImpl implements BarService {
    private final RecipeProxy recipeProxy;

    public BarServiceImpl(RecipeProxy recipeProxy) {
        this.recipeProxy = recipeProxy;
    }

    private Optional<Cocktail> prepareCocktail(String cocktailName) {
        try {
            var recipeResponse = recipeProxy.findRecipe(cocktailName);
            var cocktail = new Cocktail(recipeResponse.getBody());
            return Optional.of(cocktail);
        } catch(FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public Order getCocktail(String cocktailName) {
        var order = new Order();
        var cocktail = prepareCocktail(cocktailName);
        cocktail.ifPresentOrElse((c) -> {
            order.setCocktail(c);
            order.setMessage("Enjoy your \"" + cocktailName + "\"!");
        }, () -> {
            order.setMessage("Sorry, we could not find the recipe of \"" + cocktailName + "\" :( Would your like to order the other one?");
        });
        return order;

    }
}
