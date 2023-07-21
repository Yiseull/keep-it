package com.keepit.domain.product.dto.request;

import com.keepit.domain.product.domain.Category;
import com.keepit.domain.product.domain.Product;

public record ProductRequest(
        String name,
        Category category,
        String startDate,
        String expirationDate
) {
    public Product toEntity() {
        return Product.builder()
                .name(this.name())
                .category(this.category())
                .startDate(this.startDate())
                .expirationDate(this.expirationDate())
                .build();
    }
}
