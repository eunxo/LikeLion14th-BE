package com.project.likelion14thbe.domain.product.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResDTO {

    @Builder
    public record ProductCreateRes(
            Long id,
            LocalDateTime createdAt
    ){

    }

    @Builder
    public record ProductGetRes(
            List<ProductInfo> datalist
    ) {

        @Builder
        public record ProductInfo(
                Long productId,
                String name,
                Integer price
        ) {
        }
    }

    @Builder
    public record ProductGetDetailRes(
            Long productId,
            String name,
            Integer price,
            float rating,
            String description
    ){

    }
}