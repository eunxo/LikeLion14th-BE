package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Review", description = "리뷰 API — 생성, 단건/목록 조회, 수정, 삭제")
@SecurityRequirement(name = "JWT TOKEN")
@RestController
@RequestMapping("/api/v1/products/{productId}/reviews")
public class ReviewController {

    @Operation(
            summary = "리뷰 생성",
            description = "특정 상품에 대해 새로운 리뷰를 생성한다. 동일 상품에 1인 1회 작성 제한."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.CreateReviewResDTO.class))),
            @ApiResponse(responseCode = "400", description = "별점 범위 초과 또는 리뷰 내용 누락", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품이 존재하지 않음", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 해당 상품에 리뷰를 작성함", content = @Content)
    })

    @PostMapping
    public ResponseEntity<ReviewResDTO.CreateReviewResDTO> createReview(
            @Parameter(description = "상품 아이디", example = "5")
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

    @Operation(
            summary = "리뷰 단건 조회",
            description = "특정 상품의 특정 리뷰 1건을 상세 조회한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 단건 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailResDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품 또는 리뷰가 존재하지 않음", content = @Content)
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResDTO.ReviewDetailResDTO> getReview(
            @Parameter(description = "상품 아이디", example = "5")
            @PathVariable Long productId,
            @Parameter(description = "리뷰 아이디", example = "1")
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

    @Operation(
            summary = "리뷰 목록 조회",
            description = "특정 상품의 리뷰 목록을 페이징 조회한다. 정렬 기준은 latest / rating 지원."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListResDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 파라미터", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "상품이 존재하지 않음", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ReviewResDTO.ReviewListResDTO> getReviewList(
            @Parameter(description = "상품 아이디", example = "5")
            @PathVariable Long productId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "정렬 기준 (latest | rating)", example = "latest",
                    schema = @Schema(allowableValues = {"latest", "rating"}))
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

    @Operation(
            summary = "리뷰 수정",
            description = "본인이 작성한 리뷰의 별점 또는 내용을 부분 수정한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.UpdateReviewResDTO.class))),
            @ApiResponse(responseCode = "400", description = "별점 범위 초과", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 리뷰가 아님", content = @Content),
            @ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않음", content = @Content)
    })
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @Parameter(description = "상품 아이디", example = "5")
            @PathVariable Long productId,
            @Parameter(description = "리뷰 아이디", example = "123")
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

    @Operation(
            summary = "리뷰 삭제",
            description = "본인이 작성한 리뷰를 삭제한다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.DeleteReviewResDTO.class))),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 리뷰가 아님", content = @Content),
            @ApiResponse(responseCode = "404", description = "리뷰가 존재하지 않음", content = @Content)
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResDTO.DeleteReviewResDTO> deleteReview(
            @Parameter(description = "상품 아이디", example = "5")
            @PathVariable Long productId,
            @Parameter(description = "리뷰 아이디", example = "123")
            @PathVariable Long reviewId
    ) {
        ReviewResDTO.DeleteReviewResDTO body = new ReviewResDTO.DeleteReviewResDTO(
                reviewId,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(body);
    }
}
