package com.project.likelion14thbe.domain.order.entity;

import com.project.likelion14thbe.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_price", nullable = false)
    @Builder.Default
    private Integer totalPrice = 0;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "pending";

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Member member;

    public void cancel() {
        this.status = "cancelled";
    }
}
