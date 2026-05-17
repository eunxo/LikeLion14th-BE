package com.project.likelion14thbe.domain.review.service.command;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;

public interface ReviewCommandService {

    String createReview(CustomUserDetails customUserDetails, Long productId, ReviewReqDTO.ReviewCreateReq reviewCreateReq);

    void updateReview(Long reviewId, ReviewReqDTO.ReviewUpdateReq reviewUpdateReq);

    void deleteReview(Long reviewId);
}
