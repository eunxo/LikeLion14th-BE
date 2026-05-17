package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;

public interface OrderCommandService {

    String createOrder(CustomUserDetails customUserDetails, OrderReqDTO.CreateOrderReqDTO createOrderReqDTO);

    void deleteOrder(CustomUserDetails customUserDetails, Long orderId);
}