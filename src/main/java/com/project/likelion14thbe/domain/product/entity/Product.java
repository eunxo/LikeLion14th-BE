package com.project.likelion14thbe.domain.product.entity;

import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "Product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "product_rating_average", precision = 3, scale = 2)
    private BigDecimal productRatingAverage;

    @Lob
    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;
}
