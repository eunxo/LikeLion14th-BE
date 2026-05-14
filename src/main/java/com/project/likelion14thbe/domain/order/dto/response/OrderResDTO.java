package com.project.likelion14thbe.domain.order.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResDTO {

    @Builder @Getter
    public static class OrderListRes {
        private List<OrderDetail> orders;
    }

    @Builder @Getter
    public static class OrderDetail {
        private String orderId;
        private String date;
        private Integer quantity;
        private Integer totalAmount;
        private String status;
        private LocalDateTime orderDateTime;
    }
}