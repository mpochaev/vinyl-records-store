package com.vinylrecordsstore.repositories;

import com.vinylrecordsstore.entities.Order;
import com.vinylrecordsstore.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    // Извлекаем все заказы определенного юзера (Без пагинации, кэшируемый)
    @Query("SELECT o FROM Order o JOIN FETCH o.vinyl WHERE o.user.id = :userId ORDER BY o.orderDate DESC")
    List<Order> findByUserIdFetchVinyl(@Param("userId") String userId);

    // Топ-10 пластинок по продажам
    @Query(value = "SELECT o.vinyl.id FROM Order o WHERE o.status = 'PURCHASED' GROUP BY o.vinyl.id ORDER BY COUNT(o) DESC",
            countQuery = "SELECT COUNT(DISTINCT o.vinyl.id) FROM Order o WHERE o.status = 'PURCHASED'")
    Page<String> findTopSellingVinylIds(Pageable pageable);

    @Query("SELECT o.vinyl.id, COUNT(o) " +
            "FROM Order o " +
            "WHERE o.status = :status AND o.vinyl.id IN :ids " +
            "GROUP BY o.vinyl.id")
    List<Object[]> countSoldByVinylIds(@Param("ids") List<String> ids,
                                       @Param("status") OrderStatus status);
}