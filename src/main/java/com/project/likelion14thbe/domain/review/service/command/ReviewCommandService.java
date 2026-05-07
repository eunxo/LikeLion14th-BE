package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {

    ReviewResDTO.CreateReviewResDTO createReview(
            Long memberId,
            Long productId,
            ReviewReqDTO.CreateReviewReqDTO request
    );
}
