package com.project.likelion14thbe.domain.product.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResDTO {

    // 홈 화면 응답용
    @Builder @Getter
    public static class HomeRes {
        private List<String> categories;
        private List<ProductItem> popularProducts;
    }

    // 상품 목록 응답용
    @Builder @Getter
    public static class ListRes {
        private List<ProductItem> products;
    }

    // 상품 상세 응답용
    @Builder @Getter
    public static class ProductDetailRes {
        private Long productId;
        private String name;
        private Double price;
        private String description;
        private List<String> images;
    }

    // 공통으로 사용하는 상품 아이템 정보
    @Builder @Getter
    public static class ProductItem {
        private Long productId;
        private String name;
        private Double price;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}