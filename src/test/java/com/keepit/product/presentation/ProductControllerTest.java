package com.keepit.product.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keepit.product.application.ProductService;
import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private ProductCreateRequest request;
    private ProductResponse response;

    @BeforeEach
    void setUp() {
        request = new ProductCreateRequest("product-1",
                Category.COSMETIC,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        Product product = Product.builder()
                .name(request.name())
                .category(request.category())
                .startDate(request.startDate())
                .expirationDate(request.expirationDate())
                .build();
        response = new ProductResponse(product);
    }

    @Test
    @DisplayName("제품 등록 API")
    void createProduct() throws Exception {
        // given
        given(productService.createProduct(any(ProductCreateRequest.class)))
                .willReturn(response);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);

        // when & then
        this.mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(responseJson));
    }

    @Test
    @DisplayName("제폼 목록 조회 API")
    void getProducts() throws Exception {
        // given
        List<ProductResponse> responses = List.of(response);
        given(productService.getProducts())
                .willReturn(responses);

        String responsesJson = objectMapper.writeValueAsString(responses);

        // when & then
        this.mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(responsesJson));
    }

    @Nested
    @DisplayName("제품 아이디로 조회 API")
    class getProduct {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            // given
            given(productService.getProduct(any(long.class)))
                    .willReturn(response);

            String responseJson = objectMapper.writeValueAsString(response);

            // when & then
            mockMvc.perform(get("/api/v1/products/{productId}", 1))
                    .andExpect(status().isOk())
                    .andExpect(content().string(responseJson));
        }

        @Test
        @DisplayName("실패")
        void fail() throws Exception {
            // given
            given(productService.getProduct(any(long.class)))
                    .willThrow(new IllegalArgumentException("제품을 찾을 수 없습니다."));

            // when & then
            mockMvc.perform(get("/api/v1/products/{productId}", 1))
                    .andExpect(status().isInternalServerError());
        }
    }
}