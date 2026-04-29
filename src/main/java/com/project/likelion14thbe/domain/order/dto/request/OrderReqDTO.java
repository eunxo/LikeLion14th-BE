package com.project.likelion14thbe.domain.order.dto.request;

import lombok.*;

public class OrderReqDTO {

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class CreateReq {
        private Long productId;
        private Integer quantity;
        private String color;
    }
}