package ru.otus.homework.popov.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.popov.controller.dto.UserDetailsDto;
import ru.otus.homework.popov.controller.dto.UserDto;
import ru.otus.homework.popov.service.UserService;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public UserDetailsDto getToken(@RequestBody UserDto userDto) {
        var user = userService.login(userDto.getUsername(), userDto.getPassword());
        return user.map(UserDetailsDto::fromDomainObject).orElseThrow(() -> new UsernameNotFoundException("Invalid login or password"));
    }

    @PostMapping("/auth/logout")
    public void logout() {
        var userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        userService.logout(userDetails.getUsername());
    }
}
