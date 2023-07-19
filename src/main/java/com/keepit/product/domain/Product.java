package com.keepit.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Category category;
    private String startDate;
    private String expirationDate;

    @Builder
    public Product(String name, Category category, String startDate, String expirationDate) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }

    @PrePersist
    public void prePersist(){
        this.startDate = this.startDate == null ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : this.startDate;
        this.expirationDate = this.expirationDate == null ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : this.expirationDate;
    }
}