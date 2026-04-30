package com.project.likelion14thbe.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class ProductReqDTO {

    @Getter
    public static class ProductCreateReq {

        @Schema(description = "상품명", example = "무드등")
        private String name;

        @Schema(description = "상품 설명", example = "감성적인 분위기를 만들어주는 무드등입니다.")
        private String description;

        @Schema(description = "상품 가격", example = "12000")
        private Integer price;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/product1.png")
        private String imageUrl;
    }
}