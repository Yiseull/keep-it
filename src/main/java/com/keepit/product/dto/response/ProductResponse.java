package com.keepit.product.dto.response;

import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponse {

    private final long id;
    private final String name;
    private final Category category;
    private final LocalDateTime startDate;
    private final LocalDateTime expirationDate;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.startDate = product.getStartDate();
        this.expirationDate = product.getExpirationDate();
    }
}
