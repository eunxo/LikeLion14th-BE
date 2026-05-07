package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewResDTO.ReviewCreateRes> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq
    ){
        Long memberId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewCommandService.createReview(reviewCreateReq, productId, memberId));
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 id를 입력하여 리뷰를 조회합니다")
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReview(
            @PathVariable Long reviewId
    ){
        return ResponseEntity.ok(reviewQueryService.getReviewDetail(reviewId));
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "한 상품에 있는 모든 리뷰를 가져온다")
    public ResponseEntity<ReviewResDTO.ReviewGetRes> getReviews(
            @PathVariable Long productId
    ){
        return ResponseEntity.ok(reviewQueryService.getReviewsByProduct(productId));
    }
}
