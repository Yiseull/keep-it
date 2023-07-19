package com.keepit.product.application;

import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import com.keepit.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = request.toEntity();
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct);
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return ProductResponse.toResponses(products);
    }

    public ProductResponse getProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다."));
        return new ProductResponse(product);
    }
}
