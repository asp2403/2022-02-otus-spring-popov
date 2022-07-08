package ru.otus.homework.popov.hw16.actuator;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.BaseUnits;
import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw16.repository.BookRepository;

import java.util.Collection;

@Component
public class Metrics {
    private final BookRepository bookRepository;
    private final MeterRegistry registry;

    public Metrics(BookRepository bookRepository, MeterRegistry registry) {
        this.bookRepository = bookRepository;
        this.registry = registry;

        Gauge.builder("gauge.bookCount", () -> bookRepository.count())
                .baseUnit(BaseUnits.OBJECTS)
                .description("The number of books in the library")
                .register(registry);
    }
}
