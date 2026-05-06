package com.project.likelion14thbe.domain.review.converter;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewConverter {

    // 1. DTO -> Entity 변환: 리뷰 생성
    public static Review toReview(ReviewReqDTO.ReviewCreateReq request) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .rating(request.getRating() != null ? request.getRating().doubleValue() : null) // DTO(Integer) -> Entity(Double) 변환
                .build();
    }

    // 2. Entity -> Response DTO 변환: 리뷰 목록 조회
    public static ReviewResDTO.ReviewListRes toReviewListRes(Review review) {
        return ReviewResDTO.ReviewListRes.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating() != null ? review.getRating().doubleValue() : null) // Entity(Double) -> DTO(Integer) 변환
                .title(review.getTitle())
                .createdAt(review.getDate() != null ? review.getDate().toString() : null)
                .build();
    }

    // 3. Entity -> Response DTO 변환: 리뷰 상세 조회
    public static ReviewResDTO.ReviewDetailRes toReviewDetailRes(Review review) {
        return ReviewResDTO.ReviewDetailRes.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating() != null ? review.getRating().doubleValue() : null)
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getDate() != null ? review.getDate().toString() : null)
                .build();
    }
}