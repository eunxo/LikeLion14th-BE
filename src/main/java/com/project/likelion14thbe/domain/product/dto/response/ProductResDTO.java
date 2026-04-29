package com.project.likelion14thbe.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductResDTO {

    @Getter
    @Builder
    public static class HomeRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "HOME200")
        private String code;

        @Schema(description = "응답 메시지", example = "홈 화면 조회 성공")
        private String message;

        @Schema(description = "홈 화면 데이터")
        private HomeResult result;
    }

    @Getter
    @Builder
    public static class HomeResult {

        @Schema(description = "홈 배너 목록")
        private List<BannerRes> banners;

        @Schema(description = "추천 상품 목록")
        private List<ProductSummaryRes> recommendProducts;

        @Schema(description = "인기 상품 목록")
        private List<ProductSummaryRes> popularProducts;
    }

    @Getter
    @Builder
    public static class BannerRes {

        @Schema(description = "배너 ID", example = "1")
        private Long bannerId;

        @Schema(description = "배너 제목", example = "봄맞이 추천 상품")
        private String title;

        @Schema(description = "배너 이미지 URL", example = "https://example.com/banner1.png")
        private String imageUrl;

        @Schema(description = "배너 클릭 시 이동 URL", example = "/products?category=spring")
        private String linkUrl;
    }

    @Getter
    @Builder
    public static class ProductListRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "PRODUCT200")
        private String code;

        @Schema(description = "응답 메시지", example = "상품 목록 조회 성공")
        private String message;

        @Schema(description = "상품 목록")
        private List<ProductSummaryRes> result;
    }

    @Getter
    @Builder
    public static class ProductSummaryRes {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "상품명", example = "무드등")
        private String name;

        @Schema(description = "상품 가격", example = "12000")
        private Integer price;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/product1.png")
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class ProductDetailRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "PRODUCT200")
        private String code;

        @Schema(description = "응답 메시지", example = "상품 개별 조회 성공")
        private String message;

        @Schema(description = "상품 상세 데이터")
        private ProductDetailResult result;
    }

    @Getter
    @Builder
    public static class ProductDetailResult {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "상품명", example = "무드등")
        private String name;

        @Schema(description = "상품 설명", example = "감성적인 분위기를 만들어주는 무드등입니다.")
        private String description;

        @Schema(description = "상품 가격", example = "12000")
        private Integer price;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/product1.png")
        private String imageUrl;

        @Schema(description = "관심 상품 여부", example = "false")
        private boolean isLiked;
    }

    @Getter
    @Builder
    public static class ProductCreateRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "PRODUCT201")
        private String code;

        @Schema(description = "응답 메시지", example = "상품 등록 성공")
        private String message;

        @Schema(description = "등록된 상품 데이터")
        private ProductCreateResult result;
    }

    @Getter
    @Builder
    public static class ProductCreateResult {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "상품명", example = "무드등")
        private String name;
    }

    @Getter
    @Builder
    public static class ProductLikeRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "LIKE200")
        private String code;

        @Schema(description = "응답 메시지", example = "관심 상품 추가 성공")
        private String message;

        @Schema(description = "관심 상품 처리 결과")
        private ProductLikeResult result;
    }

    @Getter
    @Builder
    public static class ProductLikeResult {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "관심 상품 여부", example = "true")
        private boolean liked;
    }
}