package com.project.likelion14thbe.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "Product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String category;

    private String imageUrl;

    private String description;

    @Column(nullable = false)
    private Integer stock;

    public void update(String name, Integer price, String category, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();
}