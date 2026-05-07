package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderStatus;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrder(
            Member member,
            Product product,
            OrderReqDTO.CreateOrderReqDTO request,
            Integer totalPrice
    ) {
        return Order.builder()
                .member(member)
                .product(product)
                .quantity(request.quantity())
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .address(request.address())
                .build();
    }

    public static OrderResDTO.CreateOrderResDTO toCreateOrderResDTO(Order order) {
        return new OrderResDTO.CreateOrderResDTO(
                order.getId(),
                order.getProduct().getId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }

    public static OrderResDTO.MyOrderItemDTO toMyOrderItemDTO(Order order) {
        return new OrderResDTO.MyOrderItemDTO(
                order.getId(),
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }

    public static OrderResDTO.MyOrderListResDTO toMyOrderListResDTO(Page<Order> page) {
        List<OrderResDTO.MyOrderItemDTO> orderList = page.getContent().stream()
                .map(OrderConverter::toMyOrderItemDTO)
                .toList();
        return new OrderResDTO.MyOrderListResDTO(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isLast(),
                orderList
        );
    }
}
