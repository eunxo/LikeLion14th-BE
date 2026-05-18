package com.project.likelion14thbe.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member_order")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    // 주문 취소 시간 기록
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 주문 취소
    public void cancel() {
        this.deletedAt = LocalDateTime.now();
    }

}