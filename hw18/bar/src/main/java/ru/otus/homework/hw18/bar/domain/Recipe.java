package ru.otus.homework.hw18.bar.domain;

import java.util.List;

public class Recipe {
    private final String cocktailName;
    private final List<Ingredient> ingredients;

    public Recipe(String cocktailName, List<Ingredient> ingredients) {
        this.cocktailName = cocktailName;
        this.ingredients = ingredients;
    }

    public String getCocktailName() {
        return cocktailName;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
