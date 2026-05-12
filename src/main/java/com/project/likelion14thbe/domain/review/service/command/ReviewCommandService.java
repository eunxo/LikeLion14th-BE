package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;

public interface ReviewCommandService {
    void createReview(Long productId, Long memberId, ReviewReqDTO.ReviewCreateReq req);
    void updateReview(Long reviewId, Long memberId, ReviewReqDTO.ReviewUpdateReq req);
    void deleteReview(Long reviewId, Long memberId);
}
