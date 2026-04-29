package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
public class ReviewController {

    @GetMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 id를 입력하여 리뷰를 조회합니다.")
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReview (
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ){
        // 리뷰 조회 로직~~~
        return ResponseEntity.ok(
                ReviewResDTO.ReviewDetailRes.builder().build());
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    public ResponseEntity<String> createReview (
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq
    ){
        // 리뷰 생성 로직~~~
        return ResponseEntity.ok("리뷰 생성 완료");
    }

    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 id를 입력하여 리뷰를 삭제합니다.")
    public ResponseEntity<String> deleteReview (
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ){
        // 리뷰 삭제 로직~~~
        return ResponseEntity.ok("리뷰 삭제 완료");
    }

    @PutMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 id를 입력하여 리뷰를 수정합니다.")
    public ResponseEntity<String> updateReview (
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq // ReviewCreateReq 와 동일하기 때문에 재사용
    ){
        // 리뷰 수정 로직
        return ResponseEntity.ok("리뷰 수정 완료");
    }

    @GetMapping("/products/{productId}/reviews") // {reviewId}가 빠짐
    @Operation(summary = "상품 리뷰 목록 조회", description = "특정 상품에 달린 모든 리뷰 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResDTO.ReviewDetailRes>> getProductReviews(
            @PathVariable Long productId
    ){
        // 상품 리뷰 목록 조회 로직 Mock data 활용
        List<ReviewResDTO.ReviewDetailRes> reviewList = List.of(
                ReviewResDTO.ReviewDetailRes.builder().build(), // 첫 번째 리뷰
                ReviewResDTO.ReviewDetailRes.builder().build()  // 두 번째 리뷰
        );

        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/products/reviews/{userId}") // {reviewId}가 빠짐
    @Operation(summary = "내 리뷰 목록 조회", description = "내가 작성한 모든 리뷰 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResDTO.ReviewDetailRes>> getMyReviews(
            @PathVariable Long userId
    ){
        // 내 리뷰 목록 조회 로직 Mock data 활용
        List<ReviewResDTO.ReviewDetailRes> reviewList = List.of(
                ReviewResDTO.ReviewDetailRes.builder().build(), // 첫 번째 리뷰
                ReviewResDTO.ReviewDetailRes.builder().build()  // 두 번째 리뷰
        );

        return ResponseEntity.ok(reviewList);
    }
}
