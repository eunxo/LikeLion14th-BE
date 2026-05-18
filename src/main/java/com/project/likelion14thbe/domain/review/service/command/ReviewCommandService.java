package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {

    ReviewResDTO.ReviewCreateRes createReview(ReviewReqDTO.ReviewCreateReq reviewCreateReq, Long productId, String email);

    void updateReview(Long reviewId, String email, ReviewReqDTO.ReviewChangeReq dto);

    void deleteReview(Long reviewId, String email);
}
