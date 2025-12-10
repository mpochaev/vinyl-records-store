package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data // Генерирует сеттеры/геттеры
@NoArgsConstructor // Генерирует пустой конструктор, JPA / Hibernate требуют конструктор без аргументов
@AllArgsConstructor
@ToString(exclude = "password") // Генерирует toString без пароля
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // БД генерирует id
    private Long id;

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
}