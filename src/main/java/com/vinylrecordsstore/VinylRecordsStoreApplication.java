package com.vinylrecordsstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// Чтобы заработали аннотации @Cacheable и @CacheEvict
@EnableCaching
// Чтобы работали методы с @Scheduled - для автоматической отмены заказов
@EnableScheduling
public class VinylRecordsStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinylRecordsStoreApplication.class, args);
    }

}