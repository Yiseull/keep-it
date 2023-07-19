package com.keepit.product.application;

import com.keepit.global.error.exception.ErrorCode;
import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductRequest;
import com.keepit.product.dto.response.ProductResponse;
import com.keepit.product.exception.ProductException;
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

    public ProductResponse createProduct(ProductRequest request) {
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
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        return new ProductResponse(product);
    }

    public void updateProduct(long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        product.update(request.name(), request.category(), request.startDate(), request.expirationDate());
        productRepository.save(product);
    }
}
