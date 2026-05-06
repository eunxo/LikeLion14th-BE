package com.project.likelion14thbe.domain.product.dto.request;

public class ProductReqDTO {

    public record ProductCreateReq(
            String name,
            Integer price,
            Integer quantity,
            String description
    ) {

    }
}
