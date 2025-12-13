package com.vinylrecordsstore.entities;

import com.vinylrecordsstore.enums.OrderStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VinylRecord getVinyl() {
        return vinyl;
    }

    public void setVinyl(VinylRecord vinyl) {
        this.vinyl = vinyl;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", vinyl=" + vinyl +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }

    public Order(Long id,
                 User user,
                 VinylRecord vinyl,
                 BigDecimal totalPrice,
                 LocalDateTime orderDate,
                 OrderStatus status) {
        this.id = id;
        this.user = user;
        this.vinyl = vinyl;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public Order() {
    }
}