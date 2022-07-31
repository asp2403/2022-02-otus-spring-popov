package ru.otus.homework.hw18.recipe.service;

import ru.otus.homework.hw18.recipe.domain.Recipe;

import java.util.Optional;

public interface RecipeService {
    Optional<Recipe> findRecipe(String cocktailName);
}
