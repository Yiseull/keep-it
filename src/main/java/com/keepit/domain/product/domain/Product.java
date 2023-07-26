package com.keepit.domain.product.domain;

import com.keepit.domain.storage.domain.Storage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @Builder
    private Product(String name, Category category, String startDate, String expirationDate) {
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

    public void updateInfo(String name, Category category, String startDate, String expirationDate) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }

    public void addToStorage(Storage storage) {
        if (Objects.nonNull(this.storage)) {
            this.storage.getProducts().remove(this);
        }

        this.storage = storage;
        storage.getProducts().add(this);
    }
}
