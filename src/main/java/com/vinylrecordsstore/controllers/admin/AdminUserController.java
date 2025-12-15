package com.vinylrecordsstore.controllers.admin;

import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public String listCustomers(@RequestParam(defaultValue = "0") int page, Model model) {
        List<User> allCustomers = userService.getAllCustomers();
        // Пагинация
        int pageSize = 10;
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allCustomers.size());
        List<User> pageList = allCustomers.subList(fromIndex, toIndex);
        Page<User> customerPage = new PageImpl<>(pageList, PageRequest.of(page, pageSize), allCustomers.size());
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        return "admin/customers";
    }

    @PostMapping("/{id}")
    public String deleteCustomer(@PathVariable String id,
                                 @RequestParam(required = false) Integer page) {

        userService.deleteUser(id);
        int currentPage = (page != null ? page : 0);
        return "redirect:/admin/customers?page=" + currentPage;
    }
}