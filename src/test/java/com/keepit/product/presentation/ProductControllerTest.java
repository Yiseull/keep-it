package com.keepit.product.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keepit.product.application.ProductService;
import com.keepit.product.domain.Category;
import com.keepit.product.domain.Product;
import com.keepit.product.dto.request.ProductCreateRequest;
import com.keepit.product.dto.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        request = new ProductCreateRequest("제품1", Category.COSMETIC, LocalDateTime.now(), LocalDateTime.now());
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

        // when & then
        this.mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.category").value(response.getCategory().toString()))
                .andExpect(jsonPath("$.startDate").value(response.getStartDate().toString()))
                .andExpect(jsonPath("$.expirationDate").value(response.getExpirationDate().toString()));
    }
}