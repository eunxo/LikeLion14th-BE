package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;

public interface OrderCommandService {

    OrderResDTO.OrderCreateRes createOrder(OrderReqDTO.OrderCreateReq orderCreateReq, Long jwtMemberId);
}
