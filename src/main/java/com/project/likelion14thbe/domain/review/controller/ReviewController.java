package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API", description = "리뷰 생성, 조회, 수정, 삭제 기능을 제공합니다.")
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성")
    public CustomResponse<String> createReview(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody ReviewReqDTO.ReviewCreateReq req
    ) {
        reviewCommandService.createReview(productId, customUserDetails.getUsername(), req);
        return CustomResponse.onSuccess("리뷰 생성 완료");
    }

    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정")
    public CustomResponse<String> updateReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody ReviewReqDTO.ReviewUpdateReq req
    ) {
        reviewCommandService.updateReview(reviewId, customUserDetails.getUsername(), req);
        return CustomResponse.onSuccess("리뷰 수정 완료");
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public CustomResponse<String> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        reviewCommandService.deleteReview(reviewId, customUserDetails.getUsername());
        return CustomResponse.onSuccess("리뷰 삭제 완료");
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회")
    public CustomResponse<ReviewResDTO.ReviewDetailRes> getReview(@PathVariable Long reviewId) {
        return CustomResponse.onSuccess(reviewQueryService.getReview(reviewId));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회")
    public CustomResponse<ReviewResDTO.ReviewListRes> getReviewList(@PathVariable Long productId) {
        return CustomResponse.onSuccess(reviewQueryService.getReviewsByProduct(productId));
    }
}