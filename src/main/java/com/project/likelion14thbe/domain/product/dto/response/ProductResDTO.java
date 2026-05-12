package com.project.likelion14thbe.domain.product.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResDTO {

    @Builder @Getter
    public static class HomeRes {
        private List<String> categories;
        private List<ProductItem> popularProducts;
        private List<ProductItem> newProducts;
    }

    @Builder @Getter
    public static class ListRes {
        private List<ProductItem> products;
    }

    @Builder @Getter
    public static class ProductDetailRes {
        private Long productId;
        private String name;
        private Integer price;
        private String description;
        private List<String> images;
    }

    @Builder @Getter
    public static class ProductItem {
        private Long productId;
        private String name;
        private Integer price;
        private String imageUrl;
        private LocalDateTime createdAt;
    }
}