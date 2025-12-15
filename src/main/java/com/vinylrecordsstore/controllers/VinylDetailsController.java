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

    // Страница отдельной пластинки
    @GetMapping("/vinyl/{id}")
    public String getVinyl(@PathVariable String id, Model model) {
        VinylRecord vinyl = vinylRecordService.getById(id);
        model.addAttribute("vinyl", vinyl);
        return "vinyl-details";
    }
}