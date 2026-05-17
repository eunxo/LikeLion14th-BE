package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;

public interface OrderCommandService {
    void createOrder(Long memberId, String email, OrderReqDTO.CreateReq req);
    void deleteOrder(Long orderId, String email);
}