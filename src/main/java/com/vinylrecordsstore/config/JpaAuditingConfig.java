package com.vinylrecordsstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Отвечает за автоматическое заполнение createdAt и updatedAt в BaseEntity
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}