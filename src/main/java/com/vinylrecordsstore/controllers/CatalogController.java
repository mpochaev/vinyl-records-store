package com.vinylrecordsstore.controllers;

import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.enums.Genre;
import com.vinylrecordsstore.services.VinylRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final VinylRecordService vinylRecordService;

    @GetMapping("/vinyl/{id}")
    public String vinylDetails(@PathVariable Long id, Model model) {
        VinylRecord vinyl = vinylRecordService.getById(id);
        model.addAttribute("vinyl", vinyl);
        return "vinyl-details";
    }

    @GetMapping
    public String catalogPage(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        // Конвертация строки жанра в enum
        Genre genreEnum = null;
        if (genre != null && !genre.isBlank()) {
            try {
                genreEnum = Genre.valueOf(genre);
            } catch (IllegalArgumentException ignored) {
                // невалидный жанр - оставляем null
            }
        }

        BigDecimal min = null;
        if (minPrice != null && !minPrice.isBlank()) {
            try {
                min = new BigDecimal(minPrice);
            } catch (NumberFormatException ignored) {
                // некорректное число - оставляем null
            }
        }

        BigDecimal max = null;
        if (maxPrice != null && !maxPrice.isBlank()) {
            try {
                max = new BigDecimal(maxPrice);
            } catch (NumberFormatException ignored) {
                // некорректное число - оставляем null
            }
        }

        Page<VinylRecord> vinylPage =
                vinylRecordService.searchVinyls(title, artist, genreEnum, min, max, page);

        model.addAttribute("vinyls", vinylPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vinylPage.getTotalPages());

        // Чтобы сохранить фильтры в форме/пагинации
        model.addAttribute("titleFilter", title);
        model.addAttribute("artistFilter", artist);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("minPriceFilter", minPrice);
        model.addAttribute("maxPriceFilter", maxPrice);
        model.addAttribute("genres", Genre.values());

        return "catalog";
    }
}