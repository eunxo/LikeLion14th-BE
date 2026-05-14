package com.project.likelion14thbe.domain.review.entity;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.global.entity.BaseEntity;
import com.project.likelion14thbe.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_rating")
    private Double reviewRating;

    @Lob
    @Column(name = "review_content", columnDefinition = "TEXT")
    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void updatedReview(String newReviewContent, Double newReviewRating){
        this.reviewContent = newReviewContent;
        this.reviewRating = newReviewRating;
    }
}
