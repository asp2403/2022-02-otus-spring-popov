package ru.otus.homework.popov.controller.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.otus.homework.popov.domain.User;

import java.util.List;

public class UserDetailsDto {
    private String username;
    private String fullName;
    private List<SimpleGrantedAuthority> roles;
    private String token;

    public UserDetailsDto(String username, String fullName, List<SimpleGrantedAuthority> roles, String token) {
        this.username = username;
        this.fullName = fullName;
        this.roles = roles;
        this.token = token;
    }

    public UserDetailsDto() {
    }

    public static UserDetailsDto fromDomainObject(User user) {
        return new UserDetailsDto(user.getUsername(), user.getFullName(), user.getRoles(), user.getToken());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<SimpleGrantedAuthority> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
