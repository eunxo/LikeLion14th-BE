package com.project.likelion14thbe.domain.product.dto.request;

import lombok.*;

public class ProductReqDTO {

    @Getter
    public static class CreateReq {
        private String name;
        private Integer price;
        private Integer stock;
        private String category;
        private String imageUrl;
        private String description;
    }
}