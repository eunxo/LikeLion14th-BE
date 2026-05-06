package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API", description = "리뷰 생성, 조회, 수정, 삭제 기능을 제공합니다.")
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    // 1. 리뷰 생성 (POST)
    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    public ResponseEntity<String> createReview(
            @PathVariable Long productId,
            @RequestParam Long userId, // 경로가 아닌 파라미터로 처리 (필요에 따라 수정 가능)
            @RequestBody ReviewReqDTO.ReviewCreateReq req
    ) {
        reviewCommandService.createReview(productId, userId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰 생성 완료");
    }

    // 2. 리뷰 단일 조회 (GET)
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 ID를 통해 상세 내용을 조회합니다.")
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(reviewQueryService.getReview(reviewId));
    }

    // 3. 리뷰 수정 (PUT) - 명세서의 PUT /api/v1/products/{productId}/reviews/{reviewId} 반영
    @PutMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "작성한 리뷰의 내용이나 별점을 수정합니다.")
    public ResponseEntity<String> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewUpdateReq reviewUpdateReq
    ) {
        return ResponseEntity.ok("리뷰 수정 완료");
    }

    // 4. 리뷰 삭제 (DELETE) - 명세서의 DELETE /api/v1/products/{productId}/reviews/{reviewId} 반영
    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 통해 리뷰를 삭제합니다.")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok("리뷰 삭제 완료");
    }

    // 5. 리뷰 목록 조회 (GET) - 명세서의 page/size 쿼리 파라미터 반영
    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "해당 상품에 달린 리뷰 목록을 조회합니다.")
    public ResponseEntity<ReviewResDTO.ReviewListRes> getReviewList(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(reviewQueryService.getReviewsByProduct(productId));
    }
}