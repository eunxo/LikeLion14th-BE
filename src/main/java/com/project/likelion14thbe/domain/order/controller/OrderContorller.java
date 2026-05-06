package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "주문 API", description = "주문 관련 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderContorller {

    private final OrderQueryService orderQueryService;
    private final OrderCommandService orderCommandService;

    @PostMapping("/orders")
    @Operation(summary = "주문 생성", description = "주문 내역을 생성합니다")
    public ResponseEntity<String> createOrder(
            @RequestBody OrderReqDTO.CreateOrderReqDTO createOrderReqDTO
    ){
        return ResponseEntity.ok(orderCommandService.createOrder(createOrderReqDTO));
    }

    @GetMapping("/orders/list/{memberId}")
    @Operation(summary = "내 주문 목록 조회", description = "내 주문 목록을 조회합니다.")
    public ResponseEntity<List<OrderResDTO.OrderDeatilRes>> getMyOrders(
            @PathVariable Long memberId) {
        List<OrderResDTO.OrderDeatilRes> orderList = orderQueryService.getOrderList(memberId);

        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/orders/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "주문 상세 내역을 조회합니다.")
    public ResponseEntity<OrderResDTO.OrderDeatilRes> getOrderDetail(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderQueryService.getOrder(orderId));
    }

    @PostMapping("/orders/{orderId}")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long orderId) {
        // 주문 취소 로직~~~
        return ResponseEntity.ok("주문 취소 성공");
    }
}
