package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.services.OrderService;
import com.vinylrecordsstore.services.VinylRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final VinylRecordService vinylRecordService;

    @PostMapping("/order/{vinylId}")
    public String placeOrder(@AuthenticationPrincipal User currentUser,
                             @PathVariable String vinylId) {

        if (currentUser == null) {
            return "redirect:/login";
        }

        VinylRecord vinyl = vinylRecordService.getById(vinylId);
        orderService.placeOrder(currentUser, vinyl);

        return "redirect:/order_success";
    }

    @GetMapping("/order_success")
    public String orderSuccessPage() {
        return "order-success";
    }
}