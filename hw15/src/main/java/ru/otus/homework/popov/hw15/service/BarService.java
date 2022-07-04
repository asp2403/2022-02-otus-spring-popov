package ru.otus.homework.popov.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw15.domain.Cocktail;
import ru.otus.homework.popov.hw15.domain.Ingredient;
import ru.otus.homework.popov.hw15.domain.Receipt;

import java.util.List;
import java.util.Optional;

@Service
public class BarService {
    public Optional<Cocktail> prepare(Optional<Receipt> receipt) {
        var cocktail = receipt.map(
                r -> {
                    r.getIngredients().forEach(
                            i -> System.out.println("Adding " + i.getName() + "...")
                    );
                    System.out.println("Preparing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {

                    }
                    System.out.println("Ready");
                    return Optional.of(new Cocktail(r));
                }
        ).orElse(Optional.empty());
        return cocktail;
    }
}
