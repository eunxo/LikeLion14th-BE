package com.project.likelion14thbe.domain.product.dto.request;

import lombok.Getter;
import lombok.Setter;

public class ProductReqDTO {
    public record ProductCreateReq(
            Double price,
            Long quantity,
            String discription
    ) {

    }

    public record ProductDeleteReq(
            Double price,
            Long quantity,
            String discription
    ) {

    }
}
