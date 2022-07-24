package ru.otus.homework.hw18.bar.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.hw18.bar.domain.Cocktail;
import ru.otus.homework.hw18.bar.service.BarService;

@RestController
public class BarController {
    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @GetMapping("/cocktail/{cocktail}")
    public ResponseEntity<Cocktail> getCocktail(@PathVariable("cocktail") String cocktailName) {
        var cocktail = barService.prepareCocktail(cocktailName);
        return ResponseEntity.of(cocktail);
    }
}
