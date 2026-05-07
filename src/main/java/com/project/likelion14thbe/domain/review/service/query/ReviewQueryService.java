package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewQueryService {
    ReviewResDTO.ReviewDetailRes getReview(Long reviewId);
    ReviewResDTO.ReviewListRes getReviewsByProduct(Long productId);
}
