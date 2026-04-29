package com.project.likelion14thbe.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResDTO {

    @Schema(description = "상품 목록 조회 — 개별 상품 아이템")
    public record ProductItemDTO(
            @Schema(description = "상품 ID", example = "1")
            Long productId,

            @Schema(description = "상품명", example = "사과")
            String name,

            @Schema(description = "가격", example = "3000")
            Integer price,

            @Schema(description = "카테고리", example = "과일")
            String category,

            @Schema(description = "썸네일 URL", example = "https://example.com/thumb/1.jpg")
            String thumbnailUrl
    ) {
    }

    @Schema(description = "상품 목록 조회 응답 DTO (페이징 포함)")
    public record ProductListResDTO(
            @Schema(description = "전체 상품 개수", example = "50")
            Long totalElements,

            @Schema(description = "전체 페이지 수", example = "5")
            Integer totalPages,

            @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
            Integer currentPage,

            @Schema(description = "페이지당 개수", example = "10")
            Integer size,

            @Schema(description = "마지막 페이지 여부", example = "false")
            Boolean isLast,

            @Schema(description = "상품 목록")
            List<ProductItemDTO> productList
    ) {
    }

    @Schema(description = "상품 상세 조회 응답 DTO")
    public record ProductDetailResDTO(
            @Schema(description = "상품 ID", example = "1")
            Long productId,

            @Schema(description = "상품명", example = "사과")
            String name,

            @Schema(description = "가격", example = "3000")
            Integer price,

            @Schema(description = "카테고리", example = "과일")
            String category,

            @Schema(description = "상품 설명", example = "신선한 사과입니다.")
            String description,

            @Schema(description = "썸네일 URL", example = "https://example.com/thumb/1.jpg")
            String thumbnailUrl,

            @Schema(description = "등록 일시", example = "2026-04-29T09:00:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "상품 추가 응답 DTO")
    public record CreateProductResDTO(
            @Schema(description = "상품 ID", example = "51")
            Long productId,

            @Schema(description = "상품명", example = "바나나")
            String name,

            @Schema(description = "가격", example = "2000")
            Integer price,

            @Schema(description = "등록 일시", example = "2026-04-29T11:00:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "상품 삭제 응답 DTO")
    public record DeleteProductResDTO(
            @Schema(description = "상품 ID", example = "51")
            Long productId,

            @Schema(description = "삭제 일시", example = "2026-04-29T11:30:00")
            LocalDateTime deletedAt
    ) {
    }
}
