package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;


@RestController
@RequiredArgsConstructor
@Tag(name = "주문 API", description = "주문 생성 및 내역 조회 API")
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/members/{memberId}/orders")
    @Operation(summary = "주문하기", description = "상품 ID와 수량을 입력하여 주문을 생성합니다.")
    public CustomResponse<String> createOrder(
            @PathVariable Long memberId,
            @RequestBody OrderReqDTO.CreateReq req
    ) {
        orderCommandService.createOrder(memberId, req);
        return CustomResponse.onSuccess("주문 성공");
    }

    @GetMapping("/members/{memberId}/orders")
    @Operation(summary = "내 주문 목록 조회")
    public CustomResponse<OrderResDTO.OrderListRes> getOrders(@PathVariable Long memberId) {
        return CustomResponse.onSuccess(orderQueryService.getOrderHistory(memberId));
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문 번호를 입력하여 주문을 취소합니다.")
    public CustomResponse<String> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam Long memberId
    ) {
        orderCommandService.deleteOrder(orderId, memberId);
        return CustomResponse.onSuccess("주문 취소 성공");
    }
}
