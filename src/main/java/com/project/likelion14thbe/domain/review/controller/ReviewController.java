package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.controller.docs.ReviewDocs;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController implements ReviewDocs {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @Override
    @PostMapping("/products/{productId}/reviews")
    public CustomResponse<ReviewResDTO.CreateReviewResDTO> createReview(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReviewReqDTO.CreateReviewReqDTO request
    ) {
        ReviewResDTO.CreateReviewResDTO body =
                reviewCommandService.createReview(user.getUsername(), productId, request);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "리뷰 작성 성공", body);
    }

    @Override
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    public CustomResponse<ReviewResDTO.ReviewDetailResDTO> getReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "리뷰 상세 조회 성공", reviewQueryService.getReview(productId, reviewId));
    }

    @Override
    @GetMapping("/products/{productId}/reviews")
    public CustomResponse<ReviewResDTO.ReviewListResDTO> getReviewList(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "리뷰 목록 조회 성공", reviewQueryService.getReviewList(productId, page, size, sort));
    }

    @Override
    @PatchMapping("/products/{productId}/reviews/{reviewId}")
    public CustomResponse<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReviewReqDTO.UpdateReviewReqDTO request
    ) {
        return CustomResponse.onSuccess(
                reviewCommandService.updateReview(user.getUsername(), productId, reviewId, request)
        );
    }

    @Override
    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    public CustomResponse<String> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        reviewCommandService.deleteReview(user.getUsername(), productId, reviewId);
        return CustomResponse.onSuccess("리뷰 삭제 성공");
    }

    @Override
    @GetMapping("/members/me/reviews")
    public CustomResponse<ReviewResDTO.MyReviewListResDTO> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "내 리뷰 목록 조회 성공", reviewQueryService.getMyReviews(user.getUsername(), page, size));
    }
}
