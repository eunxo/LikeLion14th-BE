package com.project.likelion14thbe.domain.product.entity;

import com.project.likelion14thbe.global.BaseEntity;
import com.project.likelion14thbe.domain.order.entity.OrderItem;

import com.project.likelion14thbe.domain.product.dto.request.ProductReqDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.project.likelion14thbe.domain.member.entity.Member;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    // soft delete를 위한 필드 추가
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    // 재고 차감 비즈니스 로직
    public void decreaseQuantity(Long count) {
        if (this.quantity < count) {
            throw new IllegalArgumentException("상품의 재고가 부족합니다.");
        }
        this.quantity -= count;
    }

    // 재고 추가 비즈니스 로직
    public void increaseQuantity(Long count) {
        this.quantity += count;
    }

    // 상품 수정 메서드
    public void updateProduct(ProductReqDTO.UpdateProductReq dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
        this.category = dto.category();
        this.imageUrl = dto.productImage();
        this.quantity = dto.quantity();
    }

    // soft delete 메서드
    public void delete() {this.deletedAt = LocalDateTime.now();}
}
