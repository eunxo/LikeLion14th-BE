package com.project.likelion14thbe.domain.review.converter;


import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.ReviewCreateReq request, Product product, Member member, OrderItem orderItem) {
        return Review.builder()
                .product(product)
                .member(member)
                .orderItem(orderItem)
                .content(request.getContent())
                .rating(request.getRating())
                .build();
    }

    public static ReviewResDTO.ReviewCreateResult toCreateResult(Review review) {
        return ReviewResDTO.ReviewCreateResult.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProduct().getProductId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public static ReviewResDTO.ReviewSummaryRes toSummary(Review review) {
        return ReviewResDTO.ReviewSummaryRes.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProduct().getProductId())
                .writerName(review.getMember().getName())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public static ReviewResDTO.ReviewUpdateResult toUpdateResult(Review review) {
        return ReviewResDTO.ReviewUpdateResult.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public static ReviewResDTO.ReviewDetailResult toDetail(Review review) {
        return ReviewResDTO.ReviewDetailResult.builder()
                .reviewId(review.getReviewId())
                .productId(review.getProduct().getProductId())
                .writerName(review.getMember().getName())
                .content(review.getContent())
                .rating(review.getRating())
                .comments(java.util.List.of())
                .build();
    }
}
