package com.project.likelion14thbe.domain.order.service.query;

import java.util.List;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderQueryService {

    OrderResDTO.OrderDeatilRes getOrder(Long orderId);

    List<OrderResDTO.OrderDeatilRes> getOrderList(Long memberId);
}
