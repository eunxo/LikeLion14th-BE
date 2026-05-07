package com.project.likelion14thbe.domain.review.converter;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {
    public static Review toReview(ReviewReqDTO.ReviewCreateReq req, Member member, Product product) {
        return Review.builder()
                .content(req.getContent())
                .rating(req.getRating())
                .member(member)
                .product(product)
                .build();
    }

    public static ReviewResDTO.ReviewDetailRes toReviewDetailRes(Review review) {
        return ReviewResDTO.ReviewDetailRes.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewListRes toReviewListRes(List<Review> reviews) {
        return ReviewResDTO.ReviewListRes.builder()
                .reviews(reviews.stream()
                        .map(ReviewConverter::toReviewDetailRes)
                        .collect(Collectors.toList()))
                .build();
    }
}