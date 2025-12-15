package com.vinylrecordsstore.repositories;

import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface VinylRecordRepository extends JpaRepository<VinylRecord, String> {

    @Query("""
            SELECT v FROM VinylRecord v
            WHERE (:title = '' OR LOWER(v.title) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:artist = '' OR LOWER(v.artist) LIKE LOWER(CONCAT('%', :artist, '%')))
              AND (:genre IS NULL OR v.genre = :genre)
              AND (:minPrice IS NULL OR v.price >= :minPrice)
              AND (:maxPrice IS NULL OR v.price <= :maxPrice)
            """)
    Page<VinylRecord> searchVinyls(String title,
                                   String artist,
                                   Genre genre,
                                   BigDecimal minPrice,
                                   BigDecimal maxPrice,
                                   Pageable pageable);
}