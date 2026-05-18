package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;

public interface ReviewCommandService {

    void createReview(final Long productId, final String email, final ReviewReqDTO.ReviewCreateReq req);

    void updateReview(final Long reviewId, final String email, final ReviewReqDTO.ReviewUpdateReq req);

    void deleteReview(final Long reviewId, final String email);
}