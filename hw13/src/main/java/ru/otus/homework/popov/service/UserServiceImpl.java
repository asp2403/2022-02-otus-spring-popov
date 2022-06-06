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
    public Optional<String> login(String username, String password) {
        var user = userRepository.findByUsername(username);
        return user.flatMap(
            u -> {
                var passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, u.getPassword())) {
                    var token = u.getToken();
                    if (token == null) {
                        token = UUID.randomUUID().toString();
                        u.setToken(token);
                        userRepository.save(u);
                    }
                    return Optional.of(token);
                } else {
                    return Optional.empty();
                }
            }

        );

    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
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
