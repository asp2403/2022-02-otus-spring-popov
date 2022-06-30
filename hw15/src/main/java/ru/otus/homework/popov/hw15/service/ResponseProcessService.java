package ru.otus.homework.popov.hw15.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.domain.Ingredient;
import ru.otus.homework.popov.hw15.domain.Receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseProcessService {

    private static final int MAX_INGREDIENTS = 15;

    public Optional<Receipt> process(String response) {
        var ingredients = new ArrayList<Ingredient>();
        var cocktailName = "";
        if (response != null) {
            var json = new JSONObject(response);
            if (!json.isNull("drinks")) {
                var drinks = json.getJSONArray("drinks");
                if (!drinks.isEmpty()) {
                    var drink = drinks.getJSONObject(0);
                    cocktailName = drink.getString("strDrink");
                    System.out.println("Receipt found: " + cocktailName);
                    for (var i = 1; i <= MAX_INGREDIENTS; i++) {
                        var propName = "strIngredient" + i;
                        if (!drink.isNull(propName)) {
                            var ingredientName = drink.getString(propName);
                            ingredients.add(new Ingredient(ingredientName));
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        if (ingredients.isEmpty()) {
            System.out.println("Receipt not found");
            return Optional.empty();
        } else {
            return Optional.of(new Receipt(cocktailName, ingredients));
        }
    }
}
