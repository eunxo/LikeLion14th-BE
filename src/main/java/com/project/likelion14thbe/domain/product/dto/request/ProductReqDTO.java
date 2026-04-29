package com.project.likelion14thbe.domain.product.dto.request;

public class ProductReqDTO {
    public record CreateProductReq(
            String name,
            Double price,
            Integer quantity,
            String description,
            String productImage
    ) {
    }
}
