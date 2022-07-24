package ru.otus.homework.hw18.bar.service;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.otus.homework.hw18.bar.domain.Cocktail;
import ru.otus.homework.hw18.bar.feign.RecipeProxy;

import java.util.Optional;

@Service
public class BarServiceImpl implements BarService {
    private final RecipeProxy recipeProxy;

    public BarServiceImpl(RecipeProxy recipeProxy) {
        this.recipeProxy = recipeProxy;
    }

    @Override
    public Optional<Cocktail> prepareCocktail(String cocktailName) {
        try {
            var recipeResponse = recipeProxy.findRecipe(cocktailName);
            var cocktail = new Cocktail(recipeResponse.getBody());
            return Optional.of(cocktail);
        } catch(FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}
