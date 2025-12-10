package com.vinylrecordsstore.controllers.admin;

import com.vinylrecordsstore.dto.VinylDTO;
import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.enums.Genre;
import com.vinylrecordsstore.services.VinylRecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/vinyls")
@RequiredArgsConstructor
public class AdminVinylController {

    private final VinylRecordService vinylRecordService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String listVinyls(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<VinylRecord> vinylPage = vinylRecordService.getAllVinyls(page);
        model.addAttribute("vinyls", vinylPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vinylPage.getTotalPages());
        return "admin/vinyls";
    }

    @PostMapping
    public String createVinyl(@Valid @ModelAttribute("vinylDTO") VinylDTO vinylDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", Genre.values());
            model.addAttribute("vinylDTO", new VinylDTO());
            return "admin/vinyl-form";
        }
        vinylRecordService.addVinyl(vinylDTO);
        return "redirect:/admin/vinyls";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        VinylRecord vinyl = vinylRecordService.getById(id);
        VinylDTO vinylDTO = modelMapper.map(vinyl, VinylDTO.class);
        model.addAttribute("vinylDTO", vinylDTO);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("lastSupply", vinyl.getLastSupplyDate());
        model.addAttribute("formAction", "/admin/vinyls/" + id);
        return "admin/vinyl-form";
    }

    @PutMapping("/{id}")
    public String updateVinyl(@PathVariable Long id,
                              @Valid @ModelAttribute("vinylDTO") VinylDTO vinylDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", Genre.values());
            model.addAttribute("formAction", "/admin/vinyls/" + id);
            return "admin/vinyl-form";
        }
        vinylRecordService.updateVinyl(id, vinylDTO);
        return "redirect:/admin/vinyls";
    }

    @GetMapping("/add")
    public String addVinylPage(Model model) {
        model.addAttribute("vinylDTO", new VinylDTO());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("formAction", "/admin/vinyls");
        return "admin/vinyl-form";
    }

    @PostMapping("/{id}/delete")
    public String deleteVinyl(@PathVariable Long id,
                              @RequestParam(required = false) Integer page) {
        vinylRecordService.deleteById(id);
        int currentPage = page != null ? page : 0;
        return "redirect:/admin/vinyls?page=" + currentPage;
    }
}