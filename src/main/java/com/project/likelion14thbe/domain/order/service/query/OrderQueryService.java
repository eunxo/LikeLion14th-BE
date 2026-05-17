package com.project.likelion14thbe.domain.order.service.query;

import java.util.List;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;

public interface OrderQueryService {

    OrderResDTO.OrderDeatilRes getOrder(CustomUserDetails customUserDetails, Long orderId);

    List<OrderResDTO.OrderDeatilRes> getOrderList(CustomUserDetails customUserDetails);
}
