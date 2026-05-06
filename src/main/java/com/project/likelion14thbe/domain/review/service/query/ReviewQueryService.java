package com.project.likelion14thbe.domain.review.service.query;

import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

import java.util.List;

public interface ReviewQueryService {

    ReviewResDTO.ReviewDetailRes getReview(Long reviewId);

    List<ReviewResDTO.ReviewDetailRes> getMyReviews(Long memberId);
}
