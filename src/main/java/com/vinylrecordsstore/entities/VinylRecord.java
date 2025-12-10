package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vinyl_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VinylRecord implements Serializable { // Serializable - т.к. мы кэшируем в редисе

    // ID используемый при сериализации
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}