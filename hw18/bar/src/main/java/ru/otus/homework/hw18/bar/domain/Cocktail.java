package ru.otus.homework.hw18.bar.domain;

public class Cocktail {
    private final Recipe recipe;


    public Cocktail(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
