package com.keepit.domain.product.application;

import com.keepit.global.error.exception.ErrorCode;
import com.keepit.domain.product.domain.Product;
import com.keepit.domain.product.dto.request.ProductRequest;
import com.keepit.domain.product.dto.response.ProductResponse;
import com.keepit.domain.product.exception.ProductException;
import com.keepit.domain.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
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

    @Transactional
    public void updateProduct(long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        product.updateInfo(request.name(),
                request.category(),
                request.startDate(),
                request.expirationDate());
    }

    @Transactional
    public void deleteProduct(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(productId);
    }
}
