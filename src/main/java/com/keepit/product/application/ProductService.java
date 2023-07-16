package com.keepit.product.application;

import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import com.keepit.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = toEntity(request);
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct);
    }

    private static Product toEntity(ProductCreateRequest request) {
        return Product.builder()
                .name(request.name())
                .category(request.category())
                .startDate(request.startDate())
                .expirationDate(request.expirationDate())
                .build();
    }
}
