package com.vinylrecordsstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Укажите логин")
    private String login;

    @NotBlank(message = "Укажите email")
    @Email(message = "Неверный формат email")
    private String email;

    @NotBlank(message = "Укажите пароль")
    @Size(min = 5, message = "Пароль должен содержать минимум 5 символов")
    private String password;

    @NotBlank(message = "Повторите пароль")
    private String confirmPassword;
}