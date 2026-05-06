package com.project.likelion14thbe.domain.review.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {

    public static Review toReview(ReviewReqDTO.ReviewCreateReq reviewCreateReq, Member member, Product product) {
        return Review.builder()
                .reviewContent(reviewCreateReq.content())
                .reviewRating(reviewCreateReq.rating())
                .member(member)
                .product(product)
                .build();
    }

    public static ReviewResDTO.ReviewCreateRes toReviewCreateRes(Review review) {
        return ReviewResDTO.ReviewCreateRes.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResDTO.ReviewDetailRes toReviewDetailRes(Review review) {
        return ReviewResDTO.ReviewDetailRes.builder()
                .content(review.getReviewContent())
                .rating(review.getReviewRating())
                .build();
    }

    public static ReviewResDTO.ReviewGetRes.ReviewInfo toReviewInfo(Review review) {
        return ReviewResDTO.ReviewGetRes.ReviewInfo.builder()
                .reviewId(review.getId())
                .content(review.getReviewContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .rating(review.getReviewRating())
                .nickname(review.getMember().getName())
                .build();
    }

    public static ReviewResDTO.ReviewGetRes toReviewGetRes(List<ReviewResDTO.ReviewGetRes.ReviewInfo> reviewInfos) {
        return ReviewResDTO.ReviewGetRes.builder()
                .datalist(reviewInfos)
                .build();
    }
}
