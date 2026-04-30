package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.controller.docs.ReviewDocs;
import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
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
public class ReviewController implements ReviewDocs {

    @Override
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResDTO.CreateReviewResDTO> createReview(
            @PathVariable Long productId,
            @Valid @RequestBody ReviewReqDTO.CreateReviewReqDTO request
    ) {
        ReviewResDTO.CreateReviewResDTO body = new ReviewResDTO.CreateReviewResDTO(
                123L,
                productId,
                "bro",
                request.rating(),
                request.content(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    @GetMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResDTO.ReviewDetailResDTO> getReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        ReviewResDTO.ReviewDetailResDTO body = new ReviewResDTO.ReviewDetailResDTO(
                reviewId,
                productId,
                "bro",
                4.5,
                "nice!",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewResDTO.ReviewListResDTO> getReviewList(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "latest") String sort
    ) {
        List<ReviewResDTO.ReviewItemDTO> reviewList = List.of(
                new ReviewResDTO.ReviewItemDTO(1L, "bro", 4.5, "nice!", LocalDateTime.now()),
                new ReviewResDTO.ReviewItemDTO(2L, "bro", 5.0, "이 제품 정말 좋아요", LocalDateTime.now())
        );
        ReviewResDTO.ReviewListResDTO body = new ReviewResDTO.ReviewListResDTO(
                productId, 123L, 13, page, size, false, reviewList
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @PatchMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewReqDTO.UpdateReviewReqDTO request
    ) {
        ReviewResDTO.UpdateReviewResDTO body = new ReviewResDTO.UpdateReviewResDTO(
                reviewId,
                "bro",
                request.rating() != null ? request.rating() : 5.0,
                request.content() != null ? request.content() : "다시 먹어봤는데 더 맛있네요!",
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @DeleteMapping("/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResDTO.DeleteReviewResDTO> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
        ReviewResDTO.DeleteReviewResDTO body = new ReviewResDTO.DeleteReviewResDTO(
                reviewId,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }

    @Override
    @GetMapping("/members/me/reviews")
    public ResponseEntity<ReviewResDTO.MyReviewListResDTO> getMyReviews(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<ReviewResDTO.MyReviewItemDTO> reviewList = List.of(
                new ReviewResDTO.MyReviewItemDTO(
                        1L, 5L, "사과", 4.5, "nice!",
                        LocalDateTime.now(), LocalDateTime.now()
                )
        );
        ReviewResDTO.MyReviewListResDTO body = new ReviewResDTO.MyReviewListResDTO(
                7L, 1, page, size, true, reviewList
        );
        return ResponseEntity.ok(body);
    }
}
