package ru.otus.homework.popov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.popov.controller.dto.UserDto;
import ru.otus.homework.popov.service.UserService;

@RestController
public class TokenController {
    private final UserService userService;

    public TokenController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserDto userDto) {
        var token = userService.login(userDto.getUsername(), userDto.getPassword());
        return token.orElseThrow(() -> new UsernameNotFoundException("Invalid login or password"));
    }

    @PostMapping("/logout")
    public void logout() {
        var userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        userService.logout(userDetails.getUsername());
    }
}
