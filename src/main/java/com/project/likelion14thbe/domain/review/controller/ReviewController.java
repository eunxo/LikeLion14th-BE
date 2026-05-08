package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.controller.docs.ReviewDocs;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController implements ReviewDocs {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @Override
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResDTO.CreateReviewResDTO> createReview(
            @PathVariable Long productId,
            @RequestParam Long memberId,
            @Valid @RequestBody ReviewReqDTO.CreateReviewReqDTO request
    ) {
        ReviewResDTO.CreateReviewResDTO body =
                reviewCommandService.createReview(memberId, productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResDTO.ReviewDetailResDTO> getReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(reviewQueryService.getReview(productId, reviewId));
    }

    @Override
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResDTO.ReviewListResDTO> getReviewList(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        return ResponseEntity.ok(reviewQueryService.getReviewList(productId, page, size, sort));
    }

    @Override
    @PatchMapping("/products/{productId}/reviews/{reviewId}")
    public CustomResponse<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestParam Long memberId,
            @Valid @RequestBody ReviewReqDTO.UpdateReviewReqDTO request
    ) {
        return CustomResponse.onSuccess(
                reviewCommandService.updateReview(memberId, productId, reviewId, request)
        );
    }

    @Override
    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    public CustomResponse<String> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestParam Long memberId
    ) {
        reviewCommandService.deleteReview(memberId, productId, reviewId);
        return CustomResponse.onSuccess("리뷰 삭제 성공");
    }

    @Override
    @GetMapping("/members/me/reviews")
    public ResponseEntity<ReviewResDTO.MyReviewListResDTO> getMyReviews(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(reviewQueryService.getMyReviews(memberId, page, size));
    }
}
