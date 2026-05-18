package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.controller.docs.OrderDocs;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController implements OrderDocs {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @Override
    @PostMapping("/orders")
    public CustomResponse<OrderResDTO.CreateOrderResDTO> createOrder(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody OrderReqDTO.CreateOrderReqDTO request
    ) {
        OrderResDTO.CreateOrderResDTO body = orderCommandService.createOrder(user.getUsername(), request);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "주문 생성 성공", body);
    }

    @Override
    @GetMapping("/members/me/orders")
    public CustomResponse<OrderResDTO.MyOrderListResDTO> getMyOrders(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK, "내 주문 목록 조회 성공", orderQueryService.getMyOrders(user.getUsername(), page, size));
    }

    @Override
    @DeleteMapping("/orders/{orderId}")
    public CustomResponse<String> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        orderCommandService.cancelOrder(orderId, user.getUsername());
        return CustomResponse.onSuccess("주문 취소 성공");
    }
}
