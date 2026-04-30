package com.project.likelion14thbe.domain.product.dto.response;

import lombok.Builder;

public class ProductResDTO {

    @Builder
    public record ProductDetailRes(
            Long productId,
            String name,
            Double price,
            Double rating,
            Long reviewCount,
            String description,
            String productImage
    ) {
    }
}
