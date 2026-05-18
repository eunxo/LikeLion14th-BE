package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderCommandService {

    OrderResDTO.OrderCreateRes createOrder(OrderReqDTO.OrderCreateReq orderCreateReq, String email);

    void changeStatus(Long orderId, String email, OrderReqDTO.ChangeStatusDTO dto);

    void deleteOrder(Long orderId, String email);
}
