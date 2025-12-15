package com.vinylrecordsstore.dto;

import com.vinylrecordsstore.validation.UniqueEmail;
import com.vinylrecordsstore.validation.UniqueLogin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @UniqueLogin
    @NotBlank(message = "Укажите логин")
    private String login;

    @UniqueEmail
    @NotBlank(message = "Укажите email")
    @Email(message = "Неверный формат email")
    private String email;

    @NotBlank(message = "Укажите пароль")
    @Size(min = 5, message = "Пароль должен содержать минимум 5 символов")
    private String password;

    @NotBlank(message = "Повторите пароль")
    private String confirmPassword;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}