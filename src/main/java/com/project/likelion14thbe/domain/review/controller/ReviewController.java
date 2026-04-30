package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "리뷰API", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
public class ReviewController {
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 id를 입력하여 리뷰를 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상적인 응답",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReviewResDTO.ReviewDetailRes.class)))
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ){
        //리뷰 조회 로직
        return ResponseEntity.ok(
                ReviewResDTO.ReviewDetailRes.builder().build());
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    public ResponseEntity<String> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq
    ){
        //리뷰 생성 로직
        return ResponseEntity.ok("리뷰 생성 완료");
    }

    @PatchMapping("/products/{productId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "id를 바탕으로 리뷰를 수정합니다")
    public ResponseEntity<String> fixReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewFixReq reviewFixReq
    ){
        //리뷰 수정 로직
        return ResponseEntity.ok("리뷰 수정 완료");
    }

    @DeleteMapping("/products/{productId}/reviews/{reviewId}/delete")
    @Operation(summary = "리뷰 삭제", description = "id를 바탕으로 리뷰를 삭제한다")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ){
        return ResponseEntity.ok("리뷰 삭제 완료");
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "한 상품에 있는 모든 리뷰를 가져온다")
    public ResponseEntity<ReviewResDTO.ReviewGetRes> getReview(
            @PathVariable Long productId
    ){
        //리뷰 목록 조회 로직
        return ResponseEntity.ok(ReviewResDTO.ReviewGetRes.builder().build());
    }
}
