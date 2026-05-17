package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;

public interface ReviewCommandService {
    void createReview(Long productId, String email, ReviewReqDTO.ReviewCreateReq req);
    void updateReview(Long reviewId, String email, ReviewReqDTO.ReviewUpdateReq req);
    void deleteReview(Long reviewId, String email);
}