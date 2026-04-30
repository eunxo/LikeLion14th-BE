package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "주문API", description = "주문 관련 API")
@RequestMapping("/api/v1")
public class OrderContorller {
    @GetMapping("/users/{userId}/orders")
    @Operation(summary = "내 주문 목록 조회", description = "id 받아서 내 주문 목록 전체 조회")
    public ResponseEntity<OrderResDTO.OrderGetListRes> getMyOrderList(
            @PathVariable Long userId
    ){
        //내 주문 목록 조회 로직
        return ResponseEntity.ok(OrderResDTO.OrderGetListRes.builder().build());
    }

    @GetMapping("/users/{userId}/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "사용자의 주문 목록중 주문을 취소한다")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ){
        return ResponseEntity.ok("주문 취소 성공");
    }
}
