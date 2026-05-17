package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewQueryService {

    ReviewResDTO.ReviewDetailResDTO getReview(Long productId, Long reviewId);

    ReviewResDTO.ReviewListResDTO getReviewList(Long productId, Integer page, Integer size, String sort);

    ReviewResDTO.MyReviewListResDTO getMyReviews(String email, Integer page, Integer size);
}
