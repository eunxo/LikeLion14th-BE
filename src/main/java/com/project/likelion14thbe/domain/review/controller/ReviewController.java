package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API", description = "리뷰 생성, 조회, 수정, 삭제 기능을 제공합니다.")
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    public CustomResponse<String> createReview(
            @PathVariable Long productId,
            @RequestParam Long memberId, // userId -> memberId로 변경
            @RequestBody ReviewReqDTO.ReviewCreateReq req
    ) {
        reviewCommandService.createReview(productId, memberId, req);
        return CustomResponse.onSuccess("리뷰 작성 완료");
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 ID를 통해 상세 내용을 조회합니다.")
    public CustomResponse<ReviewResDTO.ReviewDetailRes> getReview(
            @PathVariable Long reviewId
    ) {
        return CustomResponse.onSuccess(reviewQueryService.getReview(reviewId));
    }

    @PutMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "작성한 리뷰의 내용이나 별점을 수정합니다.")
    public CustomResponse<String> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Long memberId,
            @RequestBody ReviewReqDTO.ReviewUpdateReq req
    ) {
        reviewCommandService.updateReview(reviewId, memberId, req);
        return CustomResponse.onSuccess("리뷰 수정 완료");
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 통해 리뷰를 삭제합니다.")
    public CustomResponse<String> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long memberId
    ) {
        reviewCommandService.deleteReview(reviewId, memberId);
        return CustomResponse.onSuccess("리뷰 삭제 완료");
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "해당 상품에 달린 리뷰 목록을 조회합니다.")
    public CustomResponse<ReviewResDTO.ReviewListRes> getReviewList(
            @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(reviewQueryService.getReviewsByProduct(productId));
    }
}