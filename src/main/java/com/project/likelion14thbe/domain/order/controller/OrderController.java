package com.project.likelion14thbe.domain.order.controller;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.service.command.OrderCommandService;
import com.project.likelion14thbe.domain.order.service.query.OrderQueryService;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order", description = "주문 관련 API")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("")
    @Operation(summary = "주문 생성")
    public ResponseEntity<OrderResDTO.OrderCreateResDto> createOrder(
            @RequestBody OrderReqDTO.CreateOrderReq request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        OrderResDTO.OrderCreateResDto response = orderCommandService.createOrder(request, userDetails.getMemberId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "내 주문 목록 조회")
    public ResponseEntity<List<OrderResDTO.OrderHistoryRes>> getMyOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(orderQueryService.getMyOrders(userDetails.getMemberId()));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "내 주문 상세 조회")
    public ResponseEntity<OrderResDTO.OrderDetailResDto> getMyOrderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderQueryService.getOrder(orderId));
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "주문 상태 변경")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderReqDTO.UpdateOrderStatusReq request
    ) {
        orderCommandService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok("주문 상태가 변경되었습니다.");
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 취소")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        orderCommandService.cancelOrder(orderId, userDetails.getMemberId());
        return ResponseEntity.ok("주문이 취소되었습니다.");
    }

    @GetMapping("")
    @Operation(summary = "전체 주문 목록 조회")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResDTO.OrderHistoryRes>> getOrders() {
        return ResponseEntity.ok(orderQueryService.getOrderList());
    }
}