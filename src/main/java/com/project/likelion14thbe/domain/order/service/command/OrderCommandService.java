package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;

public interface OrderCommandService {
    void createOrder(final String email, final OrderReqDTO.CreateReq req);
    void deleteOrder(Long orderId, String email);
}