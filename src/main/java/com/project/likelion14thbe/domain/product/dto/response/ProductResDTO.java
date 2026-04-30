package com.project.likelion14thbe.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "상품 목록 응답 DTO")
    public static class ProductResponseDTO {

        @Schema(description = "상품 고유 ID", example = "1")
        private Long productId;

        @Schema(description = "상품 이름", example = "Minimal Stand")
        private String name;

        @Schema(description = "상품 가격", example = "25000")
        private Integer price;

        @Schema(description = "상품 대표 이미지 URL", example = "https://image.com/stand.jpg")
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "상품 상세 조회 응답 DTO")
    public static class ProductDetailRes {
        @Schema(description = "상품 고유 ID", example = "1")
        private Long productId;

        @Schema(description = "상품 이름", example = "Minimal Stand")
        private String name;

        @Schema(description = "상품 상세 설명", example = "어디에나 잘 어울리는 미니멀한 디자인의 스탠드입니다.")
        private String description;

        @Schema(description = "상품 가격", example = "25000")
        private Integer price;

        @Schema(description = "카테고리", example = "Furniture")
        private String category;

        @Schema(description = "상품 이미지 URL", example = "https://image.com/stand.jpg")
        private String imageUrl;
    }
}