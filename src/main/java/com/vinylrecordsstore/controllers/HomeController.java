package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final OrderService orderService;

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal com.vinylrecordsstore.entities.User currentUser, Model model) {
        // Персонализированное приветствие
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getLogin());
        }
        // Топ-10 продающихся виниловых пластинок
        List<VinylRecord> topVinyls = orderService.getTopSellingVinyls();
        model.addAttribute("topVinyls", topVinyls);
        List<String> ids = topVinyls.stream().map(VinylRecord::getId).toList();
        model.addAttribute("topSoldMap", orderService.getSoldMapByVinylIds(ids));
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}