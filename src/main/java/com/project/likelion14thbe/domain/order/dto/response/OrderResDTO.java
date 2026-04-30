package com.project.likelion14thbe.domain.order.dto.response;

import lombok.Builder;
import java.util.List;
import java.math.BigDecimal;

public class OrderResDTO {

    @Builder
    public record OrderGetListRes(
            List<OrderInfo> datalist
    ) {

        // 리스트 안에 들어갈 개별 주문 데이터
        @Builder
        public record OrderInfo(
                Long orderId,
                int quantity,
                BigDecimal totalPrice,
                String status,
                String orderDate
        ) {
        }
    }
}
