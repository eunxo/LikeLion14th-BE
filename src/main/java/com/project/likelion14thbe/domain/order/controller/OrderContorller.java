package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "주문 API", description = "주문 관련 API")
@RequestMapping("/api/v1")
public class OrderContorller {

    @GetMapping("/orders/{userId}")
    @Operation(summary = "내 주문 목록 조회", description = "내 주문 목록을 조회합니다.")
    public ResponseEntity<List<OrderResDTO.OrderDeatilRes>> getMyOrders (
            @PathVariable Long userId
    ){
        // 내 주문 목록 조회 로직 Mock data 활용
        List<OrderResDTO.OrderDeatilRes> orderList = List.of(
                OrderResDTO.OrderDeatilRes.builder().build(),
                OrderResDTO.OrderDeatilRes.builder().build()
        );

        return ResponseEntity.ok(orderList);
    }

    @PostMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    public ResponseEntity<String> deleteOrder (
            @PathVariable Long orderId
    ){
        // 주문 취소 로직~~~
        return ResponseEntity.ok("주문 취소 성공");
    }
}
