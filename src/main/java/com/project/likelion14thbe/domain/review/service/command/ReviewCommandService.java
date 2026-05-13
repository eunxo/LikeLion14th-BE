package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {

    ReviewResDTO.CreateReviewResDTO createReview(
            Long memberId,
            Long productId,
            ReviewReqDTO.CreateReviewReqDTO request
    );

    ReviewResDTO.UpdateReviewResDTO updateReview(
            Long memberId,
            Long productId,
            Long reviewId,
            ReviewReqDTO.UpdateReviewReqDTO request
    );

    void deleteReview(Long memberId, Long productId, Long reviewId);
}
