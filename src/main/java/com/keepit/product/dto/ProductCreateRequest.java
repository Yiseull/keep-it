package com.keepit.product.dto;

import com.keepit.product.domain.Category;

import java.time.LocalDateTime;

public record ProductCreateRequest(
        String name,
        Category category,
        LocalDateTime startDate,
        LocalDateTime expirationDate
) {
}
