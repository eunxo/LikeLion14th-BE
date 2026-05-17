package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {

    ReviewResDTO.CreateReviewResDTO createReview(
            String email,
            Long productId,
            ReviewReqDTO.CreateReviewReqDTO request
    );

    ReviewResDTO.UpdateReviewResDTO updateReview(
            String email,
            Long productId,
            Long reviewId,
            ReviewReqDTO.UpdateReviewReqDTO request
    );

    void deleteReview(String email, Long productId, Long reviewId);
}
