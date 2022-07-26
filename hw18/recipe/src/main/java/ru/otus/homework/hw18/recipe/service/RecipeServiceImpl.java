package ru.otus.homework.hw18.recipe.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import ru.otus.homework.hw18.recipe.config.AppConfig;
import ru.otus.homework.hw18.recipe.domain.Ingredient;
import ru.otus.homework.hw18.recipe.domain.Recipe;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final AppConfig appConfig;

    private final RestTemplate rest;

    private static final int MAX_INGREDIENTS = 15;

    public RecipeServiceImpl(AppConfig appConfig, RestTemplate rest) {
        this.appConfig = appConfig;
        this.rest = rest;
    }

    private Optional<Recipe> extractRecipe(String responseStr) {
        var ingredients = new ArrayList<Ingredient>();
        var cocktailName = "";
        if (responseStr != null) {
            var json = new JSONObject(responseStr);
            if (!json.isNull("drinks")) {
                var drinks = json.getJSONArray("drinks");
                if (!drinks.isEmpty()) {
                    var drink = drinks.getJSONObject(0);
                    cocktailName = drink.getString("strDrink");
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
            return Optional.empty();
        } else {
            return Optional.of(new Recipe(cocktailName, ingredients));
        }
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
    }, fallbackMethod="findRecipeFallback")
    @Override
    public Optional<Recipe> findRecipe(String cocktailName) {

        var url = appConfig.getUrl();
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("cocktail", cocktailName);
        var response = rest.getForEntity(UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(uriVariables).encode().toUriString(), String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return extractRecipe(response.getBody());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Recipe> findRecipeFallback(String cocktailName) {
        return Optional.empty();
    }

}
