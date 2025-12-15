package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.Role;
import jakarta.persistence.*;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@ToString(exclude = "password") // Генерирует toString без пароля
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Говорим JPA как сохранять enum в базу
    @Column(nullable = false)
    private Role role;

    // Реализация интерфейса UserDetails, «формы» пользователя

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() { // Коллекция любых объектов, которая реализуют GrantedAuthority
        // У каждого юзера есть одна роль
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    // Spring Security должен знать по какому полю мы логинимся
    @Override
    @NonNull
    public String getUsername() {
        return login;
    }

    // Spring Security должен знать пароль
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public User() {
    }
}