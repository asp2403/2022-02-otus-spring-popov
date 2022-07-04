package ru.otus.homework.popov.hw15.domain;

import java.util.List;

public class Receipt {
    private final String cocktailName;
    private final List<Ingredient> ingredients;

    public Receipt(String cocktailName, List<Ingredient> ingredients) {
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
