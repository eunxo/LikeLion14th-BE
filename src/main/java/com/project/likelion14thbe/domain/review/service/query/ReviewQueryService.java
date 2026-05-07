package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

import java.util.List;

public interface ReviewQueryService {

    List<ReviewResDTO.ReviewSummaryRes> getReviews(Long productId);

    ReviewResDTO.ReviewDetailResult getReview(Long reviewId);
}
