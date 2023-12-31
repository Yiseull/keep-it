package com.keepit.domain.product.dto.response;

import com.keepit.domain.product.domain.Category;
import com.keepit.domain.product.domain.Product;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductResponse {

    private final long id;
    private final String name;
    private final Category category;
    private final String startDate;
    private final String expirationDate;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.startDate = product.getStartDate();
        this.expirationDate = product.getExpirationDate();
    }

    public static List<ProductResponse> toResponses(List<Product> products) {
        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }
}
