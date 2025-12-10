package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.services.VinylRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VinylDetailsController {

    private final VinylRecordService vinylRecordService;

    public VinylDetailsController(VinylRecordService vinylRecordService) {
        this.vinylRecordService = vinylRecordService;
    }

    @GetMapping("/vinyl/{id}")
    public String getVinyl(@PathVariable Long id, Model model) {
        VinylRecord vinyl = vinylRecordService.getById(id);
        if (vinyl == null) {
            // можно кинуть своё исключение, которое перехватит GlobalExceptionHandler
            throw new RuntimeException("Пластинка не найдена");
        }
        model.addAttribute("vinyl", vinyl);
        return "vinyl-details";
    }
}