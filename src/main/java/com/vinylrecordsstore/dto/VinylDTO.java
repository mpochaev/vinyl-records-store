package com.vinylrecordsstore.dto;

import com.vinylrecordsstore.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VinylDTO {
    private Long id;

    @NotBlank(message = "Название обязательно")
    private String title;

    @NotBlank(message = "Исполнитель обязателен")
    private String artist;

    @NotNull(message = "Выберите жанр")
    private Genre genre;

    @NotNull(message = "Укажите дату выхода")
    @PastOrPresent(message = "Дата выхода не может быть в будущем")
    private LocalDate releaseDate;

    @NotBlank(message = "Добавьте описание")
    private String description;

    @NotNull(message = "Укажите цену")
    @Min(value = 1, message = "Цена должна быть больше 0")
    private BigDecimal price;

    @NotNull(message = "Укажите количество")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer quantity;

    // Фото опционально
    private String photo;
}