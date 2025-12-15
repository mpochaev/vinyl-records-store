package com.vinylrecordsstore.dto;

import com.vinylrecordsstore.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VinylDTO {
    private String id;

    @NotBlank(message = "Название обязательно")
    private String title;

    @NotBlank(message = "Исполнитель обязателен")
    private String artist;

    @NotNull(message = "Выберите жанр")
    private Genre genre;

    @NotNull(message = "Укажите дату выхода")
    @PastOrPresent(message = "Дата выхода не может быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotBlank(message = "Добавьте описание")
    private String description;

    @NotNull(message = "Укажите цену")
    @Min(value = 1, message = "Цена должна быть больше 0")
    private BigDecimal price;

    @NotNull(message = "Укажите количество")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer quantity;

    // Фото опционально, если не добавить - будет placeholder
    private String photo;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPhoto() {
        return photo;
    }
}