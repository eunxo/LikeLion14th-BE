package com.project.likelion14thbe.domain.product.dto.request;

import lombok.*;

public class ProductReqDTO {

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class CreateReq {
        private String name;
        private Double price;
        private Integer stock;
        private String description;
    }
}