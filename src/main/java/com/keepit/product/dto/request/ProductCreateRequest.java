package com.keepit.product.dto.request;

import com.keepit.product.domain.Category;

public record ProductCreateRequest(
        String name,
        Category category,
        String startDate,
        String expirationDate
) {
}
