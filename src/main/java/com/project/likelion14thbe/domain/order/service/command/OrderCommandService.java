package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderCommandService {

    OrderResDTO.OrderCreateResult createOrder(String email, OrderReqDTO.OrderCreateReq request);

    void cancelOrder(String email, Long orderId);
}
