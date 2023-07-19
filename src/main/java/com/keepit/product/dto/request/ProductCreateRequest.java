package com.keepit.product.dto.request;

import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;

public record ProductCreateRequest(
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
