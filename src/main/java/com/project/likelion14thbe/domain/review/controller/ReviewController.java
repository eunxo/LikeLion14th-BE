package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandServiceImpl;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryServiceImpl;
import com.project.likelion14thbe.global.apiPayload.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Review", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewController(ReviewCommandServiceImpl reviewCommandServiceImpl, ReviewQueryServiceImpl reviewQueryServiceImpl, ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 작성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 작성 성공"),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없음")
    })
    public ResponseEntity<CustomResponse<ReviewResDTO.ReviewCreateResDto>> createReview(
            @PathVariable("productId") Long productId,

            @RequestHeader("memberId") Long memberId,
            @Valid @RequestBody ReviewReqDTO.ReviewCreateReq request
    ) {

        request.setProductId(productId);


        ReviewResDTO.ReviewCreateResDto response = reviewCommandService.createReview(memberId, request);

        return ResponseEntity.ok(CustomResponse.onSuccess(response));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "특정 상품에 대한 리뷰 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResDTO.ReviewListRes>> getReviews(@PathVariable Long productId) {
        List<ReviewResDTO.ReviewListRes> response = reviewQueryService.getReviews(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "단일 리뷰 조회", description = "특정 상품의 단일 리뷰 상세 정보를 조회합니다.")
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReviewDetail(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        ReviewResDTO.ReviewDetailRes response = reviewQueryService.getReviewDetail(productId, reviewId);
        return ResponseEntity.ok(response);
    }


}