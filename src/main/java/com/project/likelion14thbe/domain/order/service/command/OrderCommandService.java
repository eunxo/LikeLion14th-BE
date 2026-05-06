package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;

public interface OrderCommandService {

    String createOrder(OrderReqDTO.CreateOrderReqDTO createOrderReqDTO);
}