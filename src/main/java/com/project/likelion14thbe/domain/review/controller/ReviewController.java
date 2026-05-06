package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 생성", description = "상품에 대한 리뷰를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 생성 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewCreateRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewCreateRes> createReview(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.ReviewCreateRes.builder()
                        .isSuccess(true)
                        .code("REVIEW201")
                        .message("리뷰 생성 성공")
                        .result(reviewCommandService.createReview(productId, reviewCreateReq))
                        .build()
        );
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "특정 상품에 등록된 리뷰 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListRes.class))),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewListRes> getReviewList(
            @Parameter(description = "상품 ID", example = "1")
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.ReviewListRes.builder()
                        .isSuccess(true)
                        .code("REVIEW200")
                        .message("리뷰 목록 조회 성공")
                        .result(reviewQueryService.getReviews(productId))
                        .build()
        );
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 ID를 이용하여 리뷰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 단일 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailRes.class))),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailRes> getReview(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.ReviewDetailRes.builder()
                        .isSuccess(true)
                        .code("REVIEW200")
                        .message("리뷰 단일 조회 성공")
                        .result(reviewQueryService.getReview(reviewId))
                        .build()
        );
    }

    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 ID를 이용하여 리뷰 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewUpdateRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewUpdateRes> updateReview(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewUpdateReq request
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.ReviewUpdateRes.builder()
                        .isSuccess(true)
                        .code("REVIEW200")
                        .message("리뷰 수정 성공")
                        .result(
                                ReviewResDTO.ReviewUpdateResult.builder()
                                        .reviewId(reviewId)
                                        .content(request.getContent())
                                        .rating(request.getRating())
                                        .build()
                        )
                        .build()
        );
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 이용하여 리뷰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDeleteRes.class))),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.ReviewDeleteRes> deleteReview(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.ReviewDeleteRes.builder()
                        .isSuccess(true)
                        .code("REVIEW200")
                        .message("리뷰 삭제 성공")
                        .build()
        );
    }

    @PostMapping("/reviews/{reviewId}/comments")
    @Operation(summary = "댓글 등록", description = "리뷰에 댓글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 등록 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.CommentCreateRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.CommentCreateRes> createComment(
            @Parameter(description = "리뷰 ID", example = "1")
            @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.CommentCreateReq request
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.CommentCreateRes.builder()
                        .isSuccess(true)
                        .code("COMMENT201")
                        .message("댓글 등록 성공")
                        .result(
                                ReviewResDTO.CommentRes.builder()
                                        .commentId(1L)
                                        .writerName("홍길동")
                                        .content(request.getContent())
                                        .build()
                        )
                        .build()
        );
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 ID를 이용하여 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.CommentDeleteRes.class))),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    public ResponseEntity<ReviewResDTO.CommentDeleteRes> deleteComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(
                ReviewResDTO.CommentDeleteRes.builder()
                        .isSuccess(true)
                        .code("COMMENT200")
                        .message("댓글 삭제 성공")
                        .build()
        );
    }
}