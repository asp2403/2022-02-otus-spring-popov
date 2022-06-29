package ru.otus.homework.popov.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.domain.Cocktail;
import ru.otus.homework.popov.hw15.domain.Ingredient;

import java.util.List;

@Service
public class BarService {
    public Cocktail prepare(List<Ingredient> ingredients) throws InterruptedException {
        Cocktail cocktail;
        if (!ingredients.isEmpty()) {
            ingredients.forEach(i -> System.out.println("Adding " + i.getName() + "..."));
            System.out.println("Preparing...");
            Thread.sleep(3000);
            cocktail = new Cocktail(ingredients);
            System.out.println("Ready");
        } else {
            System.out.println("Nothing to prepare");
            cocktail = new Cocktail();
        }
        return cocktail;

    }
}
