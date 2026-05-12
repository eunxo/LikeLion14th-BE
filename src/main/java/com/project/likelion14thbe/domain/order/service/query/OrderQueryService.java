package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderQueryService {
    OrderResDTO.OrderListRes getOrderHistory(Long memberId);
}