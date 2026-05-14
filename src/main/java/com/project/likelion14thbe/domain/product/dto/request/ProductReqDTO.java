package com.project.likelion14thbe.domain.product.dto.request;

public class ProductReqDTO {

    public record ProductCreateReq(
            String name,
            Integer price,
            Integer quantity,
            String description
    ) {

    }

    public record ProductChangeDTO(
        Integer productPrice,
        Integer productQuantity,
        String productName,
        String description
    ){

    }
}
