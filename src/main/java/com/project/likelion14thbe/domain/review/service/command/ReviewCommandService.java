package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {

    ReviewResDTO.ReviewCreateResult createReview(String email, Long productId, ReviewReqDTO.ReviewCreateReq request);

    ReviewResDTO.ReviewUpdateResult updateReview(String email, Long reviewId, ReviewReqDTO.ReviewUpdateReq request);

    void deleteReview(String email, Long reviewId);
}
