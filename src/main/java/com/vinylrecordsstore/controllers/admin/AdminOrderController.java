package com.vinylrecordsstore.controllers.admin;

import com.vinylrecordsstore.entities.Order;
import com.vinylrecordsstore.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public String listOrders(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Order> orderPage = orderService.getAllOrders(page);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        return "admin/orders";
    }

    @PostMapping("/{orderId}/mark")
    public String markOrderPurchased(@PathVariable String orderId,
                                     @RequestParam(required = false) Integer page) {
        orderService.markOrderAsPurchased(orderId);
        int currentPage = page != null ? page : 0;
        return "redirect:/admin/orders?page=" + currentPage;
    }

    @PostMapping("/{orderId}")
    public String deleteOrder(@PathVariable String orderId,
                              @RequestParam(required = false) Integer page) {
        orderService.deleteOrder(orderId);
        int currentPage = page != null ? page : 0;
        return "redirect:/admin/orders?page=" + currentPage;
    }
}