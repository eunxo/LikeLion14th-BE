package com.project.likelion14thbe.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductReqDTO {

    @Schema(description = "상품 추가 요청 DTO")
    public record CreateProductReqDTO(
            @Schema(description = "상품명", example = "바나나")
            @NotBlank(message = "상품명은 필수입니다.")
            @Size(max = 100, message = "상품명은 100자 이하여야 합니다.")
            String name,
            @Schema(description = "가격 (0 이상)", example = "2000")
            @NotNull(message = "가격은 필수입니다.")
            @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
            Integer price,
            @Schema(description = "카테고리", example = "과일")
            @NotBlank(message = "카테고리는 필수입니다.")
            String category,
            @Schema(description = "상품 설명", example = "달콤한 바나나")
            String description
    ) {
    }

    @Schema(description = "상품 수정 요청 DTO (5주차 - 부분 갱신, null 필드는 미변경)")
    public record UpdateProductReqDTO(
            @Schema(description = "상품명 (선택)", example = "수정된 상품명")
            String name,

            @Schema(description = "가격 (선택, 0 이상)", example = "29000")
            Integer price,

            @Schema(description = "이미지 URL (선택)", example = "https://example.com/new.jpg")
            String imageUrl
    ) {
    }
}
