package ru.otus.homework.popov.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.User;
import ru.otus.homework.popov.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String username, String password) {
        var user = userRepository.findByUsername(username);
        return user.flatMap(
            u -> {
                var passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, u.getPassword())) {
                    var token = UUID.randomUUID().toString();
                    u.setToken(token);
                    userRepository.save(u);
                    return Optional.of(u);
                } else {
                    return Optional.empty();
                }
            }
        );
    }

    @Override
    public Optional<User> findByToken(String token) {
        var user = userRepository.findByToken(token);
        return user;
    }

    @Override
    public void logout(String username) {
        var user = userRepository.findByUsername(username);
        user.ifPresent(
                u -> {
                    u.setToken(null);
                    userRepository.save(u);
                }
        );
    }
}
