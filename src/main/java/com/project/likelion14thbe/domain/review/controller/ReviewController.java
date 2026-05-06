package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Review", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
public class ReviewController {

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 작성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 작성 성공"),
            @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없음")
    })
    public ResponseEntity<String> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq request
    ) {
        return ResponseEntity.ok("리뷰 작성 성공");
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "특정 상품에 대한 리뷰 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResDTO.ReviewListRes>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "단일 리뷰 조회", description = "특정 상품의 단일 리뷰 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReviewDetail(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews/me")
    @Operation(summary = "내 리뷰 조회", description = "로그인한 사용자가 작성한 리뷰 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    public ResponseEntity<List<ReviewResDTO.ReviewListRes>> getMyReviews() {
        return ResponseEntity.ok().build();
    }
}