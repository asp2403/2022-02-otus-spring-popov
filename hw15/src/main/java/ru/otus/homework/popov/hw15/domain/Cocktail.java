package ru.otus.homework.popov.hw15.domain;

import java.util.List;

public class Cocktail {
    private List<Ingredient> ingredients;

    public Cocktail(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Cocktail() {
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
