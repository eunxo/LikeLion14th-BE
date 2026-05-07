package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrder(OrderReqDTO.CreateReq req, Member member, Product product) {
        return Order.builder()
                .member(member)
                .product(product)
                .quantity(req.getQuantity())
                .build();
    }

    public static OrderResDTO.OrderDetail toOrderDetail(Order order) {
        return OrderResDTO.OrderDetail.builder()
                .orderId(order.getId().toString())
                .date(order.getCreatedAt().toString())
                .quantity(order.getQuantity())
                .totalAmount(order.getProduct().getPrice() * order.getQuantity())
                .status("ORDERED")
                .build();
    }

    public static OrderResDTO.OrderListRes toOrderListRes(List<Order> orders) {
        return OrderResDTO.OrderListRes.builder()
                .orders(orders.stream()
                        .map(OrderConverter::toOrderDetail)
                        .collect(Collectors.toList()))
                .build();
    }
}