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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

        private final ReviewCommandService reviewCommandService;
        private final ReviewQueryService reviewQueryService;

        @GetMapping("/reviews/{reviewId}")
        @Operation(summary = "리뷰 단일 조회", description = "리뷰 id를 입력하여 리뷰를 조회합니다.")
        public CustomResponse<ReviewResDTO.ReviewDetailRes> getReview(
                        @PathVariable Long reviewId) {
                return CustomResponse.onSuccess(reviewQueryService.getReview(reviewId));
        }

        @PostMapping("/product/{productId}/reviews")
        @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
        public CustomResponse<String> createReview(
                        @AuthenticationPrincipal CustomUserDetails customUserDetails,
                        @PathVariable Long productId,
                        @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq) {
                return CustomResponse.onSuccess(reviewCommandService.createReview(customUserDetails, productId, reviewCreateReq));
        }

        @DeleteMapping("/reviews/{reviewId}")
        @Operation(summary = "리뷰 삭제", description = "리뷰 id를 입력하여 리뷰를 삭제합니다.")
        public CustomResponse<String> deleteReview(
                        @AuthenticationPrincipal CustomUserDetails customUserDetails,
                        @PathVariable Long reviewId
        ){
                reviewCommandService.deleteReview(customUserDetails, reviewId);
                return CustomResponse.onSuccess("리뷰 삭제 완료");
        }

        @PutMapping("/reviews/{reviewId}")
        @Operation(summary = "리뷰 수정", description = "리뷰 id를 입력하여 리뷰를 수정합니다.")
        public CustomResponse<String> updateReview(
                        @AuthenticationPrincipal CustomUserDetails customUserDetails,
                        @PathVariable Long reviewId,
                        @RequestBody ReviewReqDTO.ReviewUpdateReq reviewUpdateReq
        ) {
                reviewCommandService.updateReview(customUserDetails, reviewId, reviewUpdateReq);
                return CustomResponse.onSuccess("리뷰 수정 완료");
        }

        @GetMapping("/products/{productId}/reviews") // {reviewId}가 빠짐
        @Operation(summary = "상품 리뷰 목록 조회", description = "특정 상품에 달린 모든 리뷰 목록을 조회합니다.")
        public ResponseEntity<List<ReviewResDTO.ReviewDetailRes>> getProductReviews(
                        @PathVariable Long productId) {
                // 상품 리뷰 목록 조회 로직 Mock data 활용
                List<ReviewResDTO.ReviewDetailRes> reviewList = List.of(
                                ReviewResDTO.ReviewDetailRes.builder().build(), // 첫 번째 리뷰
                                ReviewResDTO.ReviewDetailRes.builder().build() // 두 번째 리뷰
                );

                return ResponseEntity.ok(reviewList);
        }

        @GetMapping("/reviews/my")
        @Operation(summary = "내 리뷰 목록 조회", description = "내가 작성한 모든 리뷰 목록을 조회합니다.")
        public CustomResponse<List<ReviewResDTO.ReviewDetailRes>> getMyReviews(
                        @AuthenticationPrincipal CustomUserDetails customUserDetails
        ) {
                return CustomResponse.onSuccess(reviewQueryService.getMyReviews(customUserDetails));
        }
}
