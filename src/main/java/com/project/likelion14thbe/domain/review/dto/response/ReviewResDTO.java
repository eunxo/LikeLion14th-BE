package com.project.likelion14thbe.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Schema(description = "리뷰 생성 응답 DTO")
    public record CreateReviewResDTO(
            @Schema(description = "리뷰 ID", example = "123")
            Long reviewId,

            @Schema(description = "상품 ID", example = "5")
            Long productId,

            @Schema(description = "작성자 닉네임", example = "bro")
            String writerNickname,

            @Schema(description = "별점", example = "4.5")
            Double rating,

            @Schema(description = "리뷰 내용", example = "이 제품 정말 좋아요")
            String content,

            @Schema(description = "생성 일시", example = "2026-04-09T13:20:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "리뷰 단건 조회 응답 DTO")
    public record ReviewDetailResDTO(
            @Schema(description = "리뷰 ID", example = "1")
            Long reviewId,

            @Schema(description = "상품 ID", example = "5")
            Long productId,

            @Schema(description = "작성자 닉네임", example = "bro")
            String writerNickname,

            @Schema(description = "별점", example = "4.5")
            Double rating,

            @Schema(description = "리뷰 내용", example = "nice!")
            String content,

            @Schema(description = "생성 일시", example = "2026-04-09T13:15:00")
            LocalDateTime createdAt,

            @Schema(description = "수정 일시", example = "2026-04-09T13:15:00")
            LocalDateTime updatedAt
    ) {
    }

    @Schema(description = "리뷰 목록 조회 — 개별 리뷰 아이템")
    public record ReviewItemDTO(
            @Schema(description = "리뷰 ID", example = "1")
            Long reviewId,

            @Schema(description = "작성자 닉네임", example = "bro")
            String writerNickname,

            @Schema(description = "별점", example = "4.5")
            Double rating,

            @Schema(description = "리뷰 내용", example = "nice!")
            String content,

            @Schema(description = "생성 일시", example = "2026-04-09T13:15:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "리뷰 목록 조회 응답 DTO (페이징 포함)")
    public record ReviewListResDTO(
            @Schema(description = "상품 ID", example = "5")
            Long productId,

            @Schema(description = "전체 리뷰 개수", example = "123")
            Long totalElements,

            @Schema(description = "전체 페이지 수", example = "13")
            Integer totalPages,

            @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
            Integer currentPage,

            @Schema(description = "페이지당 개수", example = "10")
            Integer size,

            @Schema(description = "마지막 페이지 여부", example = "false")
            Boolean isLast,

            @Schema(description = "리뷰 목록")
            List<ReviewItemDTO> reviewList
    ) {
    }

    @Schema(description = "리뷰 수정 응답 DTO")
    public record UpdateReviewResDTO(
            @Schema(description = "리뷰 ID", example = "123")
            Long reviewId,

            @Schema(description = "작성자 닉네임", example = "bro")
            String writerNickname,

            @Schema(description = "별점", example = "5.0")
            Double rating,

            @Schema(description = "리뷰 내용", example = "다시 먹어봤는데 더 맛있네요!")
            String content,

            @Schema(description = "수정 일시", example = "2026-04-09T13:30:00")
            LocalDateTime updatedAt
    ) {
    }

    @Schema(description = "리뷰 삭제 응답 DTO")
    public record DeleteReviewResDTO(
            @Schema(description = "리뷰 ID", example = "123")
            Long reviewId,

            @Schema(description = "삭제 일시", example = "2026-04-09T13:40:00")
            LocalDateTime deletedAt
    ) {
    }
}
