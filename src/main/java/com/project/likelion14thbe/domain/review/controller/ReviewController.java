package com.project.likelion14thbe.domain.review.controller;

import com.project.likelion14thbe.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion14thbe.domain.review.dto.response.ReviewResDTO;
import com.project.likelion14thbe.domain.review.service.command.ReviewCommandService;
import com.project.likelion14thbe.domain.review.service.query.ReviewQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/products/{productId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "리뷰 생성", description = "로그인한 사용자가 상품 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    public CustomResponse<ReviewResDTO.ReviewCreateResult> createReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @RequestBody ReviewReqDTO.ReviewCreateReq reviewCreateReq
    ) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED,
                reviewCommandService.createReview(userDetails.getUsername(), productId, reviewCreateReq)
        );
    }

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "특정 상품에 등록된 리뷰 목록을 조회합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공"))
    public CustomResponse<List<ReviewResDTO.ReviewSummaryRes>> getReviewList(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId
    ) {
        return CustomResponse.onSuccess(reviewQueryService.getReviews(productId));
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 단일 조회", description = "리뷰 ID를 이용하여 리뷰를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 단일 조회 성공"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public CustomResponse<ReviewResDTO.ReviewDetailResult> getReview(
            @Parameter(description = "리뷰 ID", example = "1") @PathVariable Long reviewId
    ) {
        return CustomResponse.onSuccess(reviewQueryService.getReview(reviewId));
    }

    @PatchMapping("/reviews/{reviewId}")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "리뷰 수정", description = "본인이 작성한 리뷰만 수정할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
            @ApiResponse(responseCode = "403", description = "본인 리뷰가 아님"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public CustomResponse<ReviewResDTO.ReviewUpdateResult> updateReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "리뷰 ID", example = "1") @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.ReviewUpdateReq request
    ) {
        return CustomResponse.onSuccess(
                reviewCommandService.updateReview(userDetails.getUsername(), reviewId, request)
        );
    }

    @DeleteMapping("/reviews/{reviewId}")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "리뷰 삭제", description = "본인이 작성한 리뷰만 삭제할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "본인 리뷰가 아님"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    public CustomResponse<String> deleteReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "리뷰 ID", example = "1") @PathVariable Long reviewId
    ) {
        reviewCommandService.deleteReview(userDetails.getUsername(), reviewId);
        return CustomResponse.onSuccess("리뷰 삭제 성공");
    }

    @PostMapping("/reviews/{reviewId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "댓글 등록", description = "리뷰에 댓글을 등록합니다.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "댓글 등록 성공"))
    public CustomResponse<ReviewResDTO.CommentRes> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "리뷰 ID", example = "1") @PathVariable Long reviewId,
            @RequestBody ReviewReqDTO.CommentCreateReq request
    ) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED,
                ReviewResDTO.CommentRes.builder()
                        .commentId(1L)
                        .writerName(userDetails.getUsername())
                        .content(request.getContent())
                        .build()
        );
    }

    @DeleteMapping("/comments/{commentId}")
    @SecurityRequirement(name = "JWT TOKEN")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "댓글 삭제 성공"))
    public CustomResponse<String> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long commentId
    ) {
        return CustomResponse.onSuccess("댓글 삭제 성공");
    }
}
