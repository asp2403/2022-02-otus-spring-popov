package ru.otus.homework.hw18.bar.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.hw18.bar.domain.Order;
import ru.otus.homework.hw18.bar.service.BarService;

@RestController
public class BarController {
    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @GetMapping("/order/{cocktail}")
    public Order getCocktail(@PathVariable("cocktail") String cocktailName) {
        return barService.getCocktail(cocktailName);
    }


}
