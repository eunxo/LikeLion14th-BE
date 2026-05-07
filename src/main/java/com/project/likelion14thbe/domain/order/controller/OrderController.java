package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity; // 이거 추가
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

    @PostMapping("/users/{userId}/orders")
    @Operation(summary = "주문하기", description = "상품 ID와 수량을 입력하여 주문을 생성합니다.")
    public ResponseEntity<String> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderReqDTO.CreateReq req
    ) {
        orderCommandService.createOrder(userId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body("주문 완료");
    }

    @GetMapping("/users/{userId}/orders")
    @Operation(summary = "내 주문 목록 조회")
    public ResponseEntity<OrderResDTO.OrderListRes> getOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderQueryService.getOrderHistory(userId));
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문 번호를 입력하여 주문을 취소합니다.")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok("주문 취소 완료");
    }
}
