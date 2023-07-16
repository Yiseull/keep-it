package com.keepit.product.application;

import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import com.keepit.product.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductCreateRequest request;
    private Product product;

    @BeforeEach
    void setUp() {
        request = new ProductCreateRequest("제품1", Category.COSMETIC, LocalDateTime.now(), LocalDateTime.now());
        product = Product.builder()
                .name(request.name())
                .category(request.category())
                .startDate(request.startDate())
                .expirationDate(request.expirationDate())
                .build();
    }

    @Test
    @DisplayName("제품을 등록한다.")
    void createProduct() {
        // given
        given(productRepository.save(any(Product.class)))
                .willReturn(product);

        // when
        ProductResponse result = productService.createProduct(request);

        // then
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getCategory()).isEqualTo(product.getCategory());
        assertThat(result.getStartDate()).isEqualTo(product.getStartDate());
        assertThat(result.getExpirationDate()).isEqualTo(product.getExpirationDate());

        // verify
        verify(productRepository, times(1)).save(any(Product.class));
    }
}