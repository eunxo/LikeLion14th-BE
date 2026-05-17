package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "리뷰API", description = "리뷰 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    public CustomResponse<ReviewResDTO.ReviewCreateRes> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return CustomResponse
                .onSuccess(reviewCommandService.createReview(reviewCreateReq, productId, userDetails.getUsername()));
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 id를 입력하여 리뷰를 조회합니다")
    public CustomResponse<ReviewResDTO.ReviewDetailRes> getReview(
            @PathVariable Long reviewId
    ){
        return CustomResponse
                .onSuccess(reviewQueryService.getReviewDetail(reviewId));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "한 상품에 있는 모든 리뷰를 가져온다")
    public CustomResponse<ReviewResDTO.ReviewGetRes> getReviews(
            @PathVariable Long productId
    ){
        return CustomResponse
                .onSuccess(reviewQueryService.getReviewsByProduct(productId));
    }

    @PatchMapping("/reviews/{reviewId}/update")
    @Operation(summary = "리뷰 수정", description = "리뷰의 내용과 별점을 수정합니다.")
    public CustomResponse<String> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewChangeReq update,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        reviewCommandService.updateReview(reviewId, userDetails.getUsername(), update);
        return CustomResponse.onSuccess("리뷰 수정 성공");
    }

    @DeleteMapping("/reviews/{reviewId}/delete")
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다")
    public CustomResponse<String> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        reviewCommandService.deleteReview(reviewId, userDetails.getUsername());
        return CustomResponse.onSuccess("리뷰 삭제 성공");
    }
}
