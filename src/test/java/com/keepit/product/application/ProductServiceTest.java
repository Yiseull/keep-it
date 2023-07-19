package com.keepit.product.application;

import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import com.keepit.product.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        request = new ProductCreateRequest("제품1",
                Category.COSMETIC,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
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

    @Test
    @DisplayName("제품을 모두 조회한다.")
    void getProducts() {
        // given
        given(productRepository.findAll())
                .willReturn(List.of(product));

        // when
        List<ProductResponse> result = productService.getProducts();

        // then
        assertThat(result.size()).isEqualTo(1);

        // verify
        verify(productRepository, times(1)).findAll();
    }

    @Nested
    @DisplayName("제품 아이디로 조회한다.")
    class getProduct {

        @Test
        @DisplayName("성공")
        void success() {
            // given
            given(productRepository.findById(any(long.class)))
                    .willReturn(Optional.ofNullable(product));

            // when
            ProductResponse result = productService.getProduct(product.getId());

            // then
            assertThat(result.getName()).isEqualTo(product.getName());
            assertThat(result.getCategory()).isEqualTo(product.getCategory());
            assertThat(result.getStartDate()).isEqualTo(product.getStartDate());
            assertThat(result.getExpirationDate()).isEqualTo(product.getExpirationDate());

            // verify
            verify(productRepository, times(1)).findById(any(long.class));
        }

        @Test
        @DisplayName("실패")
        void fail() {
            // given
            given(productRepository.findById(any(long.class)))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> productService.getProduct(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("제품을 찾을 수 없습니다.");

            // verify
            verify(productRepository, times(1)).findById(any(long.class));
        }
    }
}