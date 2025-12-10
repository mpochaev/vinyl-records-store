package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.dto.UserDTO;
import com.vinylrecordsstore.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            // Если уже авторизован - редирект на главную
            return "redirect:/";
        }
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        // Валидация уникального логика и пароля
        if (userService.existsByLogin(userDTO.getLogin())) {
            bindingResult.rejectValue("login", "error.login", "Логин уже используется");
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email уже используется");
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Пароли не совпадают");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        // Регистрация нового юзера
        userService.registerUser(userDTO);
        return "redirect:/login?register";
    }
}