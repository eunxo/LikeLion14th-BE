package com.project.likelion14thbe.domain.product.dto.request;

public class ProductReqDTO {
    public record CreateProductReq(
            String name,
            String description,
            Double price,
            String category,
            String productImage,
            Long quantity
    ) {
    }

    public record UpdateProductReq(
            String name,
            String description,
            Double price,
            String category,
            String productImage,
            Long quantity
    ) {
    }
}
