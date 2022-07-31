package ru.otus.homework.hw18.recipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.homework.hw18.recipe.domain.Recipe;
import ru.otus.homework.hw18.recipe.service.RecipeService;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{cocktail}")
    public ResponseEntity<Recipe> findRecipe(@PathVariable(name = "cocktail") String cocktailName) {
       var recipe = recipeService.findRecipe(cocktailName);
       return ResponseEntity.of(recipe);
    }
}
