package ru.otus.homework.popov.hw16.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw16.repository.BookRepository;

@Component
public class MyHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    public MyHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        var bookCount = bookRepository.count();
        var serverIsDown = bookCount == 0;
        if (serverIsDown) {
            return Health.down()
                .status(Status.DOWN)
                .withDetail("message", "No more books in the library!!!")
                .build();
        } else {
            return Health.up().withDetail("message", "Everything is all right").build();
        }
    }
}
