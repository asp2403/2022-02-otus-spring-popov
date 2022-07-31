package ru.otus.homework.hw18.recipe.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.otus.homework.hw18.recipe.config.AppConfig;
import ru.otus.homework.hw18.recipe.domain.Ingredient;
import ru.otus.homework.hw18.recipe.domain.Recipe;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest

class RecipeServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RecipeServiceImpl recipeService;

    @MockBean
    private AppConfig appConfig;

    private static final String url ="https://www.thecocktaildb.com/api/json/v1/1/search.php?s=russian";

    @Test
    @DisplayName("должен корректно искать рецепт")
    void shouldCorrectFindRecipe() {

        var responseBody = "{\n" +
                "    \"drinks\": [\n" +
                "        {\n" +
                "            \"idDrink\": \"11102\",\n" +
                "            \"strDrink\": \"Black Russian\",\n" +
                "            \"strIngredient1\": \"Coffee liqueur\",\n" +
                "            \"strIngredient2\": \"Vodka\",\n" +
                "            \"strIngredient3\": null,\n" +
                "            \"strIngredient4\": null,\n" +
                "            \"strIngredient5\": null,\n" +
                "            \"strIngredient6\": null,\n" +
                "            \"strIngredient7\": null,\n" +
                "            \"strIngredient8\": null,\n" +
                "            \"strIngredient9\": null,\n" +
                "            \"strIngredient10\": null,\n" +
                "            \"strIngredient11\": null,\n" +
                "            \"strIngredient12\": null,\n" +
                "            \"strIngredient13\": null,\n" +
                "            \"strIngredient14\": null,\n" +
                "            \"strIngredient15\": null,\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        when(restTemplate.getForEntity(
                        url, String.class))
          .thenReturn(new ResponseEntity<String>(responseBody, HttpStatus.OK));

        when(appConfig.getUrl()).thenReturn(url);

        var cocktailName = "Black Russian";

        var expected = Optional.of(new Recipe(cocktailName, List.of(new Ingredient("Coffee liqueur"), new Ingredient("Vodka"))));

        var actual = recipeService.findRecipe(cocktailName);

        assertThat(actual).isPresent().usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    @DisplayName("должен выдавать Optional.empty(), если рецепт не найден")
    void shouldReturnOptionalEmptyIfRecipeNotFound() {

        var responseBody = "{\n" +
                "    \"drinks\": []\n" +
                "}";

        when(restTemplate.getForEntity(
                url, String.class))
                .thenReturn(new ResponseEntity<String>(responseBody, HttpStatus.OK));

        when(appConfig.getUrl()).thenReturn(url);

        var cocktailName = "gfdgfhgdfjhgklgj";

        var actual = recipeService.findRecipe(cocktailName);

        assertThat(actual).isEmpty();

    }
}