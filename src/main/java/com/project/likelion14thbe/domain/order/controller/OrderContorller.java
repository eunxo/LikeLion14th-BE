package com.project.likelion14thbe.domain.order.controller;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity; // 이거 추가
import org.springframework.web.bind.annotation.*;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "주문 API", description = "주문 생성 및 내역 조회 API")
@RequestMapping("/api/v1")
public class OrderContorller {

    @PostMapping("/users/{userId}/orders")
    @Operation(summary = "상품 주문", description = "상품을 구매하여 주문을 생성합니다.")
    public ResponseEntity<String> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderReqDTO.CreateReq createReq) {
        return ResponseEntity.ok("주문 생성 완료");
    }

    @GetMapping("/users/{userId}/orders")
    @Operation(summary = "내 주문 목록 조회", description = "유저의 전체 주문 내역을 조회합니다.")
    public ResponseEntity<OrderResDTO.OrderListRes> getOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(OrderResDTO.OrderListRes.builder().build());
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문 번호를 입력하여 주문을 취소합니다.")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok("주문 취소 완료");
    }
}
