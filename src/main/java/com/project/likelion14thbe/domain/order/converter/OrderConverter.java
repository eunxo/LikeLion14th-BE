package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    // 1. DTO -> Entity 변환: 주문 생성
    public static Order toOrder(OrderReqDTO.CreateOrderReq request, List<OrderItem> orderItems) {
        return Order.builder()
                .totalAmount(request.getTotalAmount())
                .date(LocalDateTime.now())
                .status(null)
                .member(null)
                .orderitems(null)
                .build();
    }

    // 2. Entity -> Response DTO 변환: 주문 생성 응답
    public static OrderResDTO.OrderCreateResDto toOrderCreateResDto(Order order) {
        return OrderResDTO.OrderCreateResDto.builder()
                .orderId(order.getOrderId())
                .build();
    }

    // 3. Entity -> Response DTO 변환: 주문 상세 조회
    public static OrderResDTO.OrderDetailResDto toOrderDetailResDto(Order order) {
        return OrderResDTO.OrderDetailResDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getDate() != null ? order.getDate().toString() : null)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    // 4. Entity -> Response DTO 변환: 주문 내역 목록 조회
    public static OrderResDTO.OrderHistoryRes toOrderHistoryRes(Order order) {
        return OrderResDTO.OrderHistoryRes.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getDate() != null ? order.getDate().toString() : null)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
    public static OrderItem toOrderItem(OrderReqDTO.OrderItemReq itemReq) {
        return OrderItem.builder()
                .itemOrderId(itemReq.getProductId())
                .quantity(itemReq.getQuantity())
                // 필요한 경우 추가 로직(가격 조회 등) 작성
                .build();
    }



}