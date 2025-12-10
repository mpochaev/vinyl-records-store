package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.entities.Order;
import com.vinylrecordsstore.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final OrderService orderService;

    @GetMapping("/account")
    public String accountPage(@AuthenticationPrincipal User currentUser, Model model) {
        // Текущая информация о юзере
        model.addAttribute("user", currentUser);
        // Все заказы этого пользователя
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        model.addAttribute("orders", orders);
        return "account";
    }
}