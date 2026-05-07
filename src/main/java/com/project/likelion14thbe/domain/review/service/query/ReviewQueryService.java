package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewQueryService {

    ReviewResDTO.ReviewDetailRes getReviewDetail(Long reviewId);

    ReviewResDTO.ReviewGetRes getReviewsByProduct(Long productId);
}
