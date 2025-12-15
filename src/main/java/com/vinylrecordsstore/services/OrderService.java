package com.vinylrecordsstore.services;

import com.vinylrecordsstore.entities.Order;
import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.enums.OrderStatus;
import com.vinylrecordsstore.repositories.OrderRepository;
import com.vinylrecordsstore.repositories.VinylRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final VinylRecordRepository vinylRecordRepository;

    /**
     * Оформление заказа пользователем
     */
    @Transactional
    @CacheEvict(value = {"ordersByUser", "catalogSearch"}, allEntries = true)
    public void placeOrder(User user, VinylRecord vinyl) {
        if (vinyl.getQuantity() <= 0) {
            throw new RuntimeException("Товара нет в наличии");
        }

        // Уменьшаем количество на складе
        vinyl.setQuantity(vinyl.getQuantity() - 1);
        vinylRecordRepository.save(vinyl);

        // Создаем заказ
        Order order = new Order();
        order.setUser(user);
        order.setVinyl(vinyl);
        order.setTotalPrice(vinyl.getPrice());           // поле totalPrice
        order.setOrderDate(LocalDateTime.now());         // поле orderDate
        order.setStatus(OrderStatus.PLACED);             // статус оформлен

        orderRepository.save(order);
        log.info("User {} placed order for vinyl {}", user.getLogin(), vinyl.getTitle());
    }

    /**
     * Все заказы (для админки) с пагинацией
     */
    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(int page) {
        return orderRepository.findAll(
                PageRequest.of(
                        page,
                        10,
                        Sort.by(Sort.Direction.DESC, "orderDate")   // новые сначала
                )
        );
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ordersByUser", key = "#user.id")
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserIdFetchVinyl(user.getId());
    }

    @Transactional
    @CacheEvict(value = "ordersByUser", allEntries = true)
    public void markOrderAsPurchased(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.PURCHASED);
        orderRepository.save(order);
        log.info("Order {} marked as PURCHASED", orderId);
    }

    @Transactional
    @CacheEvict(value = "ordersByUser", allEntries = true)
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CANCELLED) {
            VinylRecord vinyl = order.getVinyl();
            vinyl.setQuantity(vinyl.getQuantity() + 1);
            vinylRecordRepository.save(vinyl);
        }

        orderRepository.delete(order);
        log.info("Deleted order {}", orderId);
    }

    @Transactional
    public void deleteOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUserIdFetchVinyl(user.getId());
        for (Order order : orders) {
            if (order.getStatus() != OrderStatus.CANCELLED) {
                VinylRecord vinyl = order.getVinyl();
                vinyl.setQuantity(vinyl.getQuantity() + 1);
                vinylRecordRepository.save(vinyl);
            }
        }
        orderRepository.deleteAll(orders);
        log.info("Deleted {} orders of user {}", orders.size(), user.getLogin());
    }

    public Map<String, Long> getSoldMapByVinylIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return Map.of();

        List<Object[]> rows = orderRepository.countSoldByVinylIds(ids, OrderStatus.PURCHASED);

        Map<String, Long> map = new HashMap<>();
        for (Object[] r : rows) {
            String vinylId = r[0].toString();
            Long sold = ((Number) r[1]).longValue();
            map.put(vinylId, sold);
        }
        return map;
    }

    @Cacheable("topVinyls")
    public List<VinylRecord> getTopSellingVinyls() {
        List<String> topIds = orderRepository
                .findTopSellingVinylIds(PageRequest.of(0, 10))
                .getContent();

        if (topIds.isEmpty()) return List.of();

        List<VinylRecord> topVinyls = vinylRecordRepository.findAllById(topIds);
        topVinyls.sort(Comparator.comparingInt(v -> topIds.indexOf(v.getId())));
        return topVinyls;
    }
}