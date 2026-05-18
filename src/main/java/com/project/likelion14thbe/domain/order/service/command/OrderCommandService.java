package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderCommandService {

    OrderResDTO.CreateOrderResDTO createOrder(String email, OrderReqDTO.CreateOrderReqDTO request);

    void cancelOrder(Long orderId, String email);
}
