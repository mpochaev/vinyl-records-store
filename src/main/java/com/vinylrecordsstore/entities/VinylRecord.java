package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.Genre;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vinyl_records")
public class VinylRecord extends BaseEntity implements Serializable { // Serializable - т.к. мы кэшируем в редисе

    // ID используемый при сериализации
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private LocalDate releaseDate; // год-месяц-день

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime lastSupplyDate; // год-месяц-день + время (часы/минуты/секунды)

    @Column
    private String photo;  // Путь к изображению в static/img

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setLastSupplyDate(LocalDateTime lastSupplyDate) {
        this.lastSupplyDate = lastSupplyDate;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getLastSupplyDate() {
        return lastSupplyDate;
    }

    public String getPhoto() {
        return photo;
    }

    public VinylRecord() {
    }

    public VinylRecord(String id, String title, String artist,
                       Genre genre, LocalDate releaseDate,
                       String description, BigDecimal price,
                       int quantity, LocalDateTime lastSupplyDate,
                       String photo) {
        setId(id);
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.lastSupplyDate = lastSupplyDate;
        this.photo = photo;
    }
}