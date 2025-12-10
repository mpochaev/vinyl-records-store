package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable { // Serializable - т.к. мы кэшируем в редисе

    // ID используемый при сериализации
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // У заказа обязательно есть User
    @JoinColumn(name = "user_id")
    private User user; // Ссылка на объект User

    @ManyToOne(optional = false)
    @JoinColumn(name = "vinyl_id")
    private VinylRecord vinyl;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}
