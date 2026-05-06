package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "주문API", description = "주문 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @GetMapping("/orders")
    @Operation(summary = "내 주문 목록 조회", description = "memberId 받아서 내 주문 목록 전체 조회")
    public ResponseEntity<OrderResDTO.OrderGetListRes> getMyOrderList(){

        Long memberId = 1L;

        return ResponseEntity.ok(orderQueryService.getMyOrderList(memberId));
    }

    @GetMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "사용자의 주문 목록중 주문을 취소한다")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long orderId
    ){
        return ResponseEntity.ok("주문 취소 성공");
    }

    @PostMapping("/orders/create")
    @Operation(summary = "주문 추가", description = "주문 상품을 추가한다")
    public ResponseEntity<OrderResDTO.OrderCreateRes> createOrder(
            @RequestBody OrderReqDTO.OrderCreateReq orderCreateReq) {

        Long memberId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderCommandService.createOrder(orderCreateReq, memberId));
    }
}
