package ru.otus.homework.hw18.bar.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.homework.hw18.bar.domain.Recipe;

@FeignClient(name = "recipe")
public interface RecipeProxy {

    @GetMapping("/recipe/{cocktail}")
    ResponseEntity<Recipe> findRecipe(@PathVariable(name = "cocktail") String cocktailName);
}
