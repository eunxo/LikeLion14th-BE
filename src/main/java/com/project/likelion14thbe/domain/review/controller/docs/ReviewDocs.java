package com.project.likelion14thbe.domain.review.controller.docs;

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
import org.springframework.http.ResponseEntity;

@Tag(name = "Review", description = "리뷰 API — 생성, 단건/목록 조회, 수정, 삭제")
@SecurityRequirement(name = "JWT TOKEN")
public interface ReviewDocs {

    @Operation(
            summary = "리뷰 생성",
            description = "특정 상품에 대해 새로운 리뷰를 생성한다. 동일 상품에 1인 1회 작성 제한. (JWT 적용 전까지 memberId는 임시 query 파라미터)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 생성 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.CreateReviewResDTO.class))),
            @ApiResponse(responseCode = "400", description = "별점 범위 초과 또는 리뷰 내용 누락", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "회원 또는 상품이 존재하지 않음", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 해당 상품에 리뷰를 작성함", content = @Content)
    })
    ResponseEntity<ReviewResDTO.CreateReviewResDTO> createReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "작성자 회원 ID (JWT 적용 전 임시)", example = "1") Long memberId,
            ReviewReqDTO.CreateReviewReqDTO request
    );

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
    ResponseEntity<ReviewResDTO.ReviewDetailResDTO> getReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "1") Long reviewId
    );

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
    ResponseEntity<ReviewResDTO.ReviewListResDTO> getReviewList(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size,
            @Parameter(description = "정렬 기준 (latest | rating)", example = "latest",
                    schema = @Schema(allowableValues = {"latest", "rating"})) String sort
    );

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
    ResponseEntity<ReviewResDTO.UpdateReviewResDTO> updateReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "123") Long reviewId,
            ReviewReqDTO.UpdateReviewReqDTO request
    );

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
    ResponseEntity<ReviewResDTO.DeleteReviewResDTO> deleteReview(
            @Parameter(description = "상품 아이디", example = "5") Long productId,
            @Parameter(description = "리뷰 아이디", example = "123") Long reviewId
    );

    @Operation(
            summary = "내 리뷰 조회",
            description = "현재 로그인한 회원이 작성한 모든 리뷰를 페이징 조회한다. (JWT 적용 전까지 memberId는 임시 query 파라미터)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 리뷰 조회 성공",
                    content = @Content(schema = @Schema(implementation = ReviewResDTO.MyReviewListResDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 파라미터", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content),
            @ApiResponse(responseCode = "404", description = "회원이 존재하지 않음", content = @Content)
    })
    ResponseEntity<ReviewResDTO.MyReviewListResDTO> getMyReviews(
            @Parameter(description = "조회 회원 ID (JWT 적용 전 임시)", example = "1") Long memberId,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") Integer page,
            @Parameter(description = "페이지당 개수", example = "10") Integer size
    );
}
