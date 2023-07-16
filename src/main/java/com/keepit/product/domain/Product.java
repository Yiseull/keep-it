package com.keepit.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;

    @Builder
    public Product(String name, Category category, LocalDateTime startDate, LocalDateTime expirationDate) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }

    @PrePersist
    public void prePersist(){
        this.startDate = this.startDate == null ? LocalDateTime.now() : this.startDate;
        this.expirationDate = this.expirationDate == null ? LocalDateTime.now() : this.expirationDate;
    }
}
