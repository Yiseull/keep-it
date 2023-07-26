package com.keepit.domain.storage.domain;

import com.keepit.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @Builder
    private Storage(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        product.addToStorage(this);
    }
}
