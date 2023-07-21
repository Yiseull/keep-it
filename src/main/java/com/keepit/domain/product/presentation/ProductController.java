package com.keepit.domain.product.presentation;

import com.keepit.domain.product.application.ProductService;
import com.keepit.domain.product.dto.request.ProductRequest;
import com.keepit.domain.product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        log.info("request={}", request);
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> responses = productService.getProducts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long productId) {
        log.info("productId={}", productId);
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable long productId,
                                              @RequestBody ProductRequest request) {
        log.info("productId={}, request={}", productId, request);
        productService.updateProduct(productId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        log.info("productId={}", productId);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
